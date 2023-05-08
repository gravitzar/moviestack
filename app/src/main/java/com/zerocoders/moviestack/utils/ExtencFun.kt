package com.zerocoders.moviestack.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

fun Modifier.applyGradient(): Modifier {
    return drawWithCache {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = size.height / 3,
            endY = size.height
        )
        onDrawWithContent {
            drawContent()
            drawRect(gradient, blendMode = BlendMode.Multiply)
        }
    }
}

fun Int.toRuntimeString(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        "${hours}h ${minutes}m"
    } else {
        "$minutes min"
    }
}

fun Float.roundToSingleFraction(): Float {
    return "%.1f".format(this).toFloat()
}

val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "SessionPrefs")

@Suppress("Deprecation")
internal fun Window.setFullScreen(isFullScreen: Boolean) {
    if (isFullScreen) {
        decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
    } else {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
    }
}

internal fun Activity.setFullScreen(isFullScreen: Boolean) {
    window.setFullScreen(isFullScreen)
}

internal fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Activity not found.")
}