package com.zerocoders.moviestack.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zerocoders.moviestack.model.Movie
import com.zerocoders.moviestack.utils.ASPECT_RATIO_16_9
import com.zerocoders.moviestack.utils.ASPECT_RATIO_1_1_5
import com.zerocoders.moviestack.utils.applyGradient
import com.zerocoders.moviestack.utils.dummyMovieList
import com.zerocoders.moviestack.widgets.common.MoviePoster
import com.zerocoders.moviestack.widgets.common.TitleText
import com.zerocoders.moviestack.widgets.common.TopBar
import com.zerocoders.moviestack.widgets.common.TopTrendingMovie
import com.zerocoders.moviestack.widgets.common.animatedHorizontalListItemsWithTitle
import com.zerocoders.moviestack.widgets.common.animatedItem

@Composable
internal fun HomeScreenRoute(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToMovieDetails: (movieId: Int) -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(isLoggedIn) {
        isLoggedIn?.let {
            if (isLoggedIn == false) {
                onNavigateToLogin()
            } else {
                viewModel.getMovies()
            }
        }
    }

    HomeScreen(state, onNavigateToMovieDetails)
}

@Composable
fun HomeScreen(
    state: HomeScreenUiState,
    onMovieClicked: (movieId: Int) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        item {
            TopBar(title = "Movies", modifier = Modifier.padding(horizontal = 8.dp))
        }
        animatedItem(state.latestMovie) { movie ->
            TopTrendingMovie(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ASPECT_RATIO_16_9)
                    .applyGradient(),
                movie = movie!!,
                onTrendingMovieClicked = { onMovieClicked(it) }
            )
        }
        animatedHorizontalListItemsWithTitle(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            listItems = state.nowPlayingMovies,
            title = {
                TitleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    title = "Now Playing Movies"
                )
            }
        ) { movie ->
            MoviePoster(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(ASPECT_RATIO_1_1_5),
                movieId = movie.id,
                movieName = movie.title,
                moviePosterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                onPosterClicked = { onMovieClicked(it) }
            )
        }
        animatedHorizontalListItemsWithTitle(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            listItems = state.upComingMovies,
            title = {
                TitleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    title = "Upcoming Movies"
                )
            }
        ) { movie ->
            MoviePoster(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(ASPECT_RATIO_1_1_5),
                movieId = movie.id,
                movieName = movie.title,
                moviePosterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                onPosterClicked = { onMovieClicked(it) }
            )
        }
        animatedHorizontalListItemsWithTitle(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            listItems = state.topRatedMovies,
            title = {
                TitleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                    title = "Top Rated"
                )
            }
        ) { movie ->
            MoviePoster(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(ASPECT_RATIO_1_1_5),
                movieId = movie.id,
                movieName = movie.title,
                moviePosterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                onPosterClicked = { onMovieClicked(it) }
            )
        }
        animatedHorizontalListItemsWithTitle(
            modifier = Modifier.padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            listItems = state.popularMovies,
            title = {
                TitleText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp), title = "Popular Movies"
                )
            }
        ) { movie ->
            MoviePoster(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(ASPECT_RATIO_1_1_5),
                movieId = movie.id,
                movieName = movie.title,
                moviePosterPath = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                onPosterClicked = { onMovieClicked(it) }
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "HomeScreen Preview"
)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        state = HomeScreenUiState(
            latestMovie = Movie(
                posterPath = null,
                adult = false,
                overview = "ultrices",
                releaseDate = null,
                genresIds = listOf(),
                genres = listOf(),
                id = 1702,
                originalTitle = "postulant",
                originalLanguage = "perpetua",
                title = "cursus",
                backdropPath = null,
                popularity = 0.1f,
                voteCount = 9817,
                video = false,
                voteAverage = 2.3f,
                runtime = 125
            ),
            topRatedMovies = dummyMovieList,
            nowPlayingMovies = dummyMovieList,
            popularMovies = dummyMovieList
        )
    )
}