package com.zerocoders.moviestack.widgets.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.zerocoders.moviestack.ui.theme.robotoMediumTextStyle

@Composable
fun TitleText(modifier: Modifier = Modifier, title: String = "") {
    Text(
        modifier = modifier,
        text = title,
        style = robotoMediumTextStyle.copy(fontSize = 18.sp),
    )
}
