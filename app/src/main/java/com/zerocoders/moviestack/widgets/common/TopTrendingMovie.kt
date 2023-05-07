package com.zerocoders.moviestack.widgets.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.zerocoders.moviestack.ui.theme.montserratMediumTextStyle
import com.zerocoders.moviestack.utils.ASPECT_RATIO_16_9
import com.zerocoders.moviestack.utils.applyGradient
import com.zerocoders.moviestack.utils.toRuntimeString
import com.zerocoders.showdiary.tmdb.Movie
import kotlin.math.min

@Composable
fun TopTrendingMovie(
    modifier: Modifier = Modifier,
    movie: Movie,
    onTrendingMovieClicked: (movieId: Int) -> Unit = { }
) {
    ConstraintLayout {
        val (topTrendingCard, topTrendingInfo) = createRefs()
        BoxWithElevation(
            color = Color.LightGray,
            modifier = modifier
                .constrainAs(topTrendingCard) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { onTrendingMovieClicked(movie.id) },
        ) {
            val painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original${movie.backdropPath}")
            val state = painter.state

            val transition by animateFloatAsState(
                targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f, label = ""
            )
            Image(
                painter = painter,
                contentDescription = movie.title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .alpha(min(1f, transition / .2f)),
                colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(transition) })
            )
        }

        Text(
            modifier = Modifier.constrainAs(topTrendingInfo) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            text = "#1 • ${movie.title} • ${movie.runtime?.toRuntimeString().orEmpty()}",
            style = montserratMediumTextStyle.copy(fontSize = 14.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopTrendingPreview() {
    TopTrendingMovie(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ASPECT_RATIO_16_9)
            .applyGradient(),
        movie = Movie(
            id = 1,
            overview = "Black adam is a super hero movie...",
            originalLanguage = "EN",
            originalTitle = "Black Adam",
            title = "Black Adam",
            backdropPath = null,
            posterPath = null,
            popularity = 8.9f,
            video = false,
            voteAverage = 8.9f,
            voteCount = 243524,
            runtime = 124
        )
    )
}