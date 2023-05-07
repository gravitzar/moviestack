package com.zerocoders.moviestack.widgets.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlin.math.min

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    movieId: Int,
    movieName: String,
    moviePosterPath: String,
    onPosterClicked: (movieId: Int) -> Unit = {}
) {
    BoxWithShape(
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.clickable { onPosterClicked(movieId) },
    ) {
        val painter = rememberAsyncImagePainter(moviePosterPath)
        val state = painter.state

        val transition by animateFloatAsState(
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )
        Image(
            painter = painter,
            contentDescription = movieName,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .scale(.8f + (.2f * transition))
                .alpha(min(1f, transition / .2f)),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviePosterPreview() {
    MoviePoster(
        modifier = Modifier,
        movieId = 12432,
        movieName = "Black Adam",
        moviePosterPath = "https://image.tmdb.org/t/p/w500/iMmMxF6f2OonUrXrHKBLSYAhXly.jpg",
    )
}