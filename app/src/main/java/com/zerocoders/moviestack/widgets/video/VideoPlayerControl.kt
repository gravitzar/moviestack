package com.zerocoders.moviestack.widgets.video

import android.util.Log
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zerocoders.moviestack.R
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun VideoPlayerControl(
    state: VideoPlayerState,
    title: String? = null,
    subtitle: String? = null,
    background: Color = Color.Black.copy(0.30f),
    contentColor: Color = Color.White,
    progressLineColor: Color = MaterialTheme.colorScheme.primary,
    onOptionsClick: (() -> Unit)? = null,
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ControlHeader(
                modifier = Modifier.fillMaxWidth(),
                title = title,
                subtitle = subtitle,
                onOptionsClick = onOptionsClick
            )
            PlaybackControl(
                isPlaying = state.isPlaying.value,
                control = state.control
            )
            TimelineControl(
                modifier = Modifier.fillMaxWidth(),
                progressLineColor = progressLineColor,
                isFullScreen = state.isFullscreen.value,
                videoDurationMs = state.videoDurationMs.value,
                videoPositionMs = state.videoPositionMs.value
            ) {
                state.control.setFullscreen(!state.isFullscreen.value)
            }
        }
    }
}

@Composable
private fun ControlHeader(
    modifier: Modifier = Modifier,
    title: String?,
    subtitle: String?,
    onOptionsClick: (() -> Unit)?,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (title != null) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = title,
                    color = LocalContentColor.current,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = LocalContentColor.current.copy(0.80f),
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        if (onOptionsClick != null) {
            AdaptiveIconButton(
                modifier = Modifier.size(SmallIconButtonSize),
                onClick = onOptionsClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.more_vert),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun PlaybackControl(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    control: VideoPlayerControl
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(
            modifier = Modifier
                .size(BigIconButtonSize)
                .padding(10.dp),
            onClick = control::rewind
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.replay_10),
                contentDescription = null
            )
        }
        IconButton(
            modifier = Modifier.size(BigIconButtonSize),
            onClick = { if (isPlaying) control.pause() else control.play() }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = if (isPlaying) R.drawable.pause else R.drawable.play),
                contentDescription = null
            )
        }
        IconButton(
            modifier = Modifier
                .size(BigIconButtonSize)
                .padding(10.dp),
            onClick = control::forward
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(id = R.drawable.forward_10),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TimelineControl(
    modifier: Modifier,
    progressLineColor: Color,
    isFullScreen: Boolean,
    videoDurationMs: Long,
    videoPositionMs: Long,
    onFullScreenToggle: () -> Unit,
) {
    val timestamp = remember(videoDurationMs, videoPositionMs.milliseconds.inWholeSeconds) {
        prettyVideoTimestamp(videoDurationMs.milliseconds, videoPositionMs.milliseconds)
    }
    val progress = remember(videoPositionMs) {
        1.0f - ((videoDurationMs - videoPositionMs) / videoDurationMs.toFloat())
    }
    Log.d("OAI", "TimelineControl: $videoDurationMs-$videoPositionMs progress: $progress")
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = timestamp)
            Spacer(modifier = Modifier.weight(1.0f))
            AdaptiveIconButton(
                modifier = Modifier.size(SmallIconButtonSize),
                onClick = onFullScreenToggle
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = if (isFullScreen) R.drawable.fullscreen_exit else R.drawable.fullscreen),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp),
            progress = 2f,
            trackColor = progressLineColor,
            color = Color.LightGray
        )
    }
}

/**
 * Allow the button to be any size instead of constraining it to 48dp
 * **/
@Composable
private fun AdaptiveIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentAlpha = if (enabled) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        CompositionLocalProvider(LocalContentColor provides contentAlpha, content = content)
    }
}

private val BigIconButtonSize = 52.dp
private val SmallIconButtonSize = 32.dp


