@file:Suppress("NOTHING_TO_INLINE")

package com.zerocoders.moviestack.widgets.video

import android.view.View
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import com.google.android.exoplayer2.video.VideoSize
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

private const val MAX_ASPECT_RATIO_DIFFERENCE_FRACTION = 0.01f

fun Modifier.adaptiveSize(fullscreen: Boolean, view: View): Modifier {
    return if (fullscreen) {
//        hideSystemBars(view)
        fillMaxSize()
    } else {
//        showSystemBars(view)
        fillMaxWidth().aspectRatio(1.7777778f)
    }
}

@JvmInline
value class ResizeMode private constructor(val value: Int) {
    companion object {
        val Fit = ResizeMode(0)
        val FixedWidth = ResizeMode(1)
        val FixedHeight = ResizeMode(2)
        val Fill = ResizeMode(3)
        val Zoom = ResizeMode(4)
    }
}

fun Modifier.defaultPlayerTapGestures(playerState: VideoPlayerState) = pointerInput(Unit) {
    detectTapGestures(
        onDoubleTap = {
            if (playerState.videoResizeMode.value == ResizeMode.Zoom) {
                playerState.control.setVideoResize(ResizeMode.Fit)
            } else {
                playerState.control.setVideoResize(ResizeMode.Zoom)
            }
        },
        onTap = {
            if (playerState.isControlUiVisible.value) {
                playerState.hideControlUi()
            } else {
                playerState.showControlUi()
            }
        }
    )
}

fun Modifier.adaptiveLayout(
    aspectRatio: Float,
    resizeMode: ResizeMode = ResizeMode.Fit
) = layout { measurable, constraints ->
    val resizedConstraint = constraints.resizeForVideo(resizeMode, aspectRatio)
    val placeable = measurable.measure(resizedConstraint)
    layout(constraints.maxWidth, constraints.maxHeight) {
        // Center x and y axis relative to the layout
        placeable.placeRelative(
            x = (constraints.maxWidth - resizedConstraint.maxWidth) / 2,
            y = (constraints.maxHeight - resizedConstraint.maxHeight) / 2
        )
    }
}

internal inline fun VideoSize.aspectRatio(): Float =
    if (height == 0 || width == 0) 0f else (width * pixelWidthHeightRatio) / height

internal inline fun Constraints.resizeForVideo(
    mode: ResizeMode,
    aspectRatio: Float
): Constraints {
    if (aspectRatio <= 0f) return this

    var width = maxWidth
    var height = maxHeight
    val constraintAspectRatio: Float = (width / height).toFloat()
    val difference = aspectRatio / constraintAspectRatio - 1

    if (abs(difference) <= MAX_ASPECT_RATIO_DIFFERENCE_FRACTION) {
        return this
    }

    when (mode) {
        ResizeMode.Fit -> {
            if (difference > 0) {
                height = (width / aspectRatio).toInt()
            } else {
                width = (height * aspectRatio).toInt()
            }
        }

        ResizeMode.Zoom -> {
            if (difference > 0) {
                width = (height * aspectRatio).toInt()
            } else {
                height = (width / aspectRatio).toInt()
            }
        }

        ResizeMode.FixedWidth -> {
            height = (width / aspectRatio).toInt()
        }

        ResizeMode.FixedHeight -> {
            width = (height * aspectRatio).toInt()
        }

        ResizeMode.Fill -> Unit
    }

    return this.copy(maxWidth = width, maxHeight = height)
}

internal inline fun prettyVideoTimestamp(
    duration: Duration,
    position: Duration
): String = buildString {
    appendMinutesAndSeconds(duration)
    append(" / ")
    appendMinutesAndSeconds(position)
}

private fun StringBuilder.appendMinutesAndSeconds(duration: Duration) {
    val minutes = duration.inWholeMinutes
    val seconds = (duration - minutes.minutes).inWholeSeconds
    appendDoubleDigit(minutes)
    append(':')
    appendDoubleDigit(seconds)
}

private fun StringBuilder.appendDoubleDigit(value: Long) {
    if (value < 10) {
        append(0)
        append(value)
    } else {
        append(value)
    }
}