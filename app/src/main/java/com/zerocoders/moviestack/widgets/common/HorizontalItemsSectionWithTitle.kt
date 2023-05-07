package com.zerocoders.moviestack.widgets.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> AnimatedHorizontalListItemsWithTitle(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    userScrollEnabled: Boolean = true,
    listItems: List<T>,
    title: @Composable () -> Unit,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit
) {
    AnimatedVisibility(
        visible = listItems.isNotEmpty(),
        enter = fadeIn(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ) + expandVertically(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        exit = fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ) + shrinkVertically(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        Column(modifier = modifier) {
            title()
            LazyRow(
                state = state,
                contentPadding = contentPadding,
                horizontalArrangement = horizontalArrangement,
                verticalAlignment = verticalAlignment,
                flingBehavior = flingBehavior,
                userScrollEnabled = userScrollEnabled,
            ) {
                items(items = listItems) {
                    itemContent(it)
                }
            }
        }
    }
}

fun <T> LazyListScope.animatedHorizontalListItemsWithTitle(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    listItems: List<T>,
    title: @Composable () -> Unit,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = item {
    AnimatedHorizontalListItemsWithTitle(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
        listItems = listItems,
        title = title,
        itemContent = itemContent
    )
}

fun <T> LazyListScope.animatedItem(
    item: T,
    itemContent: @Composable LazyItemScope.(item: T) -> Unit
) = item {
    AnimatedVisibility(
        visible = item != null,
        enter = fadeIn(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        ),
        exit = fadeOut(
            animationSpec = TweenSpec(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        itemContent(item)
    }
}