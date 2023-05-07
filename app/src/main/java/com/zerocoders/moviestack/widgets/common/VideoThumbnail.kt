package com.zerocoders.moviestack.widgets.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.zerocoders.moviestack.tmdb.Video
import com.zerocoders.moviestack.utils.ASPECT_RATIO_16_9


@Composable
fun VideoThumbnail(video: Video, onVideoClicked: (String) -> Unit) {
    Box(
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(ASPECT_RATIO_16_9)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = "https://img.youtube.com/vi/${video.key}/maxresdefault.jpg"),
            contentDescription = "Trailer thumbnail",
            modifier = Modifier
                .fillMaxSize()
                .clickable { onVideoClicked(video.key) }
        )
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play Button",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(28.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VideoThumbnailPreview() {
    VideoThumbnail(video = Video(
        id = "fabellas",
        iso639 = null,
        iso3166 = null,
        key = "parturient",
        site = null,
        name = null,
        size = null,
        type = null
    ), onVideoClicked = {
        
    })
}
