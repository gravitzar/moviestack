package com.zerocoders.moviestack.widgets.player

import androidx.compose.runtime.Stable
import com.google.android.exoplayer2.Player
import java.lang.IllegalStateException

@Stable
@Suppress("UNUSED_PARAMETER")
enum class RepeatMode(rawValue: String) {
    NONE("none"),
    ONE("one"),
    ALL("all"),
}

internal fun RepeatMode.toExoPlayerRepeatMode(): Int = when (this) {
    RepeatMode.NONE -> Player.REPEAT_MODE_OFF
    RepeatMode.ALL -> Player.REPEAT_MODE_ALL
    RepeatMode.ONE -> Player.REPEAT_MODE_ONE
}

fun Int.toRepeatMode(): RepeatMode = if (this in 0 until 3) {
    when (this) {
        0 -> RepeatMode.NONE
        1 -> RepeatMode.ONE
        2 -> RepeatMode.ALL
        else -> throw IllegalStateException("This is not ExoPlayer repeat mode.")
    }
} else {
    throw IllegalStateException("This is not ExoPlayer repeat mode.")
}