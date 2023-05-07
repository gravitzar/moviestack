package com.zerocoders.moviestack.widgets.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = TextStyle(),
    maxLines: Int = 5
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        modifier = modifier
            .clickable { expanded = !expanded }
            .animateContentSize(),
        text = text,
        maxLines = if (expanded) Int.MAX_VALUE else maxLines,
        overflow = TextOverflow.Ellipsis,
        style = style
    )
}