package com.zerocoders.moviestack.widgets.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zerocoders.moviestack.screens.profile.ProfileDialog
import com.zerocoders.moviestack.ui.theme.MovieStackTheme
import com.zerocoders.moviestack.ui.theme.montserratBlackTextStyle

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    var showProfileDialog by remember { mutableStateOf(false) }

    if (showProfileDialog) {
        ProfileDialog(onDismiss = { showProfileDialog = false })
    }

    Row(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = montserratBlackTextStyle.copy(fontSize = 38.sp)
        )
        IconButton(onClick = { showProfileDialog = true }) {
            Icon(
                modifier = Modifier.size(38.dp),
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "account settings",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "TopBar Preview",
)
@Composable
fun TestPreview() {
    MovieStackTheme {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            title = "Movies"
        )
    }
}
