package com.zerocoders.moviestack.widgets.video2

import android.view.View
import androidx.compose.runtime.Immutable
import androidx.core.view.isVisible
import com.google.android.exoplayer2.ui.StyledPlayerView

@Immutable
data class VideoPlayerControllerConfig(
    val showSpeedAndPitchOverlay: Boolean,
    val showSubtitleButton: Boolean,
    val showCurrentTimeAndTotalTime: Boolean,
    val showBufferingProgress: Boolean,
    val showForwardIncrementButton: Boolean,
    val showBackwardIncrementButton: Boolean,
    val showBackTrackButton: Boolean,
    val showNextTrackButton: Boolean,
    val showRepeatModeButton: Boolean,
    val showFullScreenButton: Boolean,
    val controllerShowTimeMilliSeconds: Int,
    val controllerAutoShow: Boolean,
) {

    companion object {
        val Default = VideoPlayerControllerConfig(
            showSpeedAndPitchOverlay = false,
            showSubtitleButton = false,
            showCurrentTimeAndTotalTime = true,
            showBufferingProgress = true,
            showForwardIncrementButton = false,
            showBackwardIncrementButton = false,
            showBackTrackButton = false,
            showNextTrackButton = false,
            showRepeatModeButton = false,
            controllerShowTimeMilliSeconds = 5_000,
            controllerAutoShow = true,
            showFullScreenButton = false,
        )
    }
}

internal fun VideoPlayerControllerConfig.applyToExoPlayerView(
    playerView: StyledPlayerView,
    onFullScreenStatusChanged: (Boolean) -> Unit,
) {
    val controllerView = playerView.rootView

    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_settings).isVisible =
        showSpeedAndPitchOverlay
    playerView.setShowSubtitleButton(showSubtitleButton)
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_time).isVisible =
        showCurrentTimeAndTotalTime
    playerView.setShowBuffering(
        if (!showBufferingProgress) StyledPlayerView.SHOW_BUFFERING_NEVER else StyledPlayerView.SHOW_BUFFERING_ALWAYS,
    )
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_ffwd_with_amount).isVisible =
        showForwardIncrementButton
    controllerView.findViewById<View>(com.google.android.exoplayer2.R.id.exo_rew_with_amount).isVisible =
        showBackwardIncrementButton
    playerView.setShowNextButton(showNextTrackButton)
    playerView.setShowPreviousButton(showBackTrackButton)
    playerView.setShowFastForwardButton(showForwardIncrementButton)
    playerView.setShowRewindButton(showBackwardIncrementButton)
    playerView.controllerShowTimeoutMs = controllerShowTimeMilliSeconds
    playerView.controllerAutoShow = controllerAutoShow

    @Suppress("DEPRECATION")
    if (showFullScreenButton) {
        playerView.setControllerOnFullScreenModeChangedListener {
            onFullScreenStatusChanged(it)
        }
    } else {
        playerView.setControllerOnFullScreenModeChangedListener(null)
    }
}