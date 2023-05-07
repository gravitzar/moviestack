package com.zerocoders.moviestack.widgets.video

import android.os.Build
import android.view.SurfaceView
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
private fun VideoPlayer(
    modifier: Modifier,
    playerState: VideoPlayerState,
    controller: @Composable () -> Unit
) {
    Box(modifier = modifier.defaultPlayerTapGestures(playerState)) {
        AndroidView(
            modifier = Modifier.adaptiveLayout(
                aspectRatio = playerState.videoSize.value.aspectRatio(),
                resizeMode = playerState.videoResizeMode.value
            ),
            factory = { context ->
                SurfaceView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }.also {
                    playerState.player.setVideoSurfaceView(it)
                }
            }
        )

        AnimatedVisibility(
            visible = playerState.isControlUiVisible.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            controller()
        }
    }
}

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    playerState: VideoPlayerState,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    controller: @Composable () -> Unit,
) {
    VideoPlayer(
        modifier = modifier,
        playerState = playerState,
        controller = controller
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> playerState.showControlUi()
                    Lifecycle.Event.ON_STOP -> playerState.player.pause()
                    else -> Unit
                }
            } else {
                when (event) {
                    Lifecycle.Event.ON_START -> playerState.showControlUi()
                    Lifecycle.Event.ON_STOP -> playerState.player.pause()
                    else -> Unit
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            playerState.player.release()
        }
    }
}