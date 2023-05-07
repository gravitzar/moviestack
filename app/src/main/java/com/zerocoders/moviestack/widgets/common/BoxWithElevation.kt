package com.zerocoders.moviestack.widgets.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

@Composable
fun BoxWithShape(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = Color.Black,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = shape
            )
            .clip(shape)
    ) {
        content()
    }
}
