package com.zerocoders.moviestack.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.zerocoders.moviestack.R
import com.zerocoders.moviestack.ui.theme.robotoBlackTextStyle

@Composable
fun ProfileDialog(
    onDismiss: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsStateWithLifecycle(initialValue = "")
    ProfileDialog(
        userName = userName,
        onDismiss = onDismiss,
        onLogOut = {
            onDismiss()
            viewModel.logout()
        }
    )
}

@Composable
fun ProfileDialog(
    userName: String,
    onDismiss: () -> Unit,
    onLogOut: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val painter = rememberAsyncImagePainter(model = R.drawable.ic_profile_placeholder)
                    Image(
                        painter = painter, contentDescription = "user profile pic",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Text(text = userName, style = robotoBlackTextStyle.copy(fontSize = 20.sp))
                    Button(onClick = { onLogOut() }, modifier = Modifier.padding(top = 4.dp)) {
                        Text(text = "LOGOUT")
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun ProfileScreenPreview() {
    ProfileDialog(onDismiss = { /*TODO*/ })
}