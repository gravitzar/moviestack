package com.zerocoders.moviestack.screens.videos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.google.android.exoplayer2.MediaItem
import com.zerocoders.moviestack.utils.debug

@Composable
internal fun VideoTrailerScreenRoute(videoId: String, viewModel: VideoTrailerViewModel = hiltViewModel()) {
    val screenState by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = videoId, block = {
    debug("OAI video id: $videoId")
        viewModel.fetchAndExtractVideoData(videoId)
    })
    VideoTrailerScreen(screenState, videoId)
}

@Composable
fun VideoTrailerScreen(screenState: VideoTrailerScreenState, videoId: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (screenState.isLoading || screenState.isError) {
            Image(
                painter = rememberAsyncImagePainter(model = "https://img.youtube.com/vi/${videoId}/maxresdefault.jpg"),
                contentDescription = "Trailer thumbnail",
                modifier = Modifier.fillMaxSize()
            )
            CircularProgressIndicator()
        }
        if (screenState.isSuccess && !screenState.videoUrl.isNullOrBlank()) {
            com.zerocoders.moviestack.widgets.video2.VideoPlayer(
                modifier = Modifier.fillMaxSize(),
                mediaItemToPlay = MediaItem.fromUri(screenState.videoUrl),
                setFullScreen = true
            )
        }
    }

}

@Preview
@Composable
fun VideoTrailerScreenTrailerPreview() {
    VideoTrailerScreen(screenState = VideoTrailerScreenState(), videoId = "videoId")
}