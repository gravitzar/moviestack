package com.zerocoders.moviestack.screens.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.zerocoders.moviestack.widgets.MoviePoster
import com.zerocoders.moviestack.widgets.common.TopBar
import com.zerocoders.moviestack.tmdb.Movie

@Composable
internal fun DiscoverMoviesRoute(
    viewModel: DiscoverMovieViewModel = hiltViewModel(),
    onNavigateToMovieDetails: (movieId: Int) -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle(initialValue = null)

    DiscoverMovies(
        viewModel.movies.collectAsLazyPagingItems(),
        onNavigateToMovieDetails
    )

    LaunchedEffect(isLoggedIn) {
        isLoggedIn?.let {
            if (isLoggedIn == false) {
                onNavigateToLogin()
            }
        }
    }
}

@Composable
fun DiscoverMovies(
    movies: LazyPagingItems<Movie>,
    onMovieClicked: (movieId: Int) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        state = rememberLazyGridState(),
        contentPadding = PaddingValues(horizontal = 8.dp),
    ) {
        item(span = {
            GridItemSpan(maxLineSpan)
        }) {
            TopBar(title = "Discover")
        }
        items(
            count = movies.itemCount,
        ) {
            val movie = movies[it]
            BoxWithConstraints {
                MoviePoster(
                    modifier = Modifier.height(maxWidth * 1.4705f),
                    movieId = movie?.id ?: 0,
                    movieName = movie?.title.orEmpty(),
                    moviePosterPath = "https://image.tmdb.org/t/p/w500${movie?.posterPath.orEmpty()}",
                    onPosterClicked = { id ->
                        onMovieClicked(id)
                    }
                )
            }
        }
        when (val state = movies.loadState.append) {
            is LoadState.Error -> {
                error(message = state.error.message ?: "")
            }

            LoadState.Loading -> {
                loading()
            }

            is LoadState.NotLoading -> Unit
        }
    }
}

private fun LazyGridScope.loading() {
    item(span = {
        GridItemSpan(maxLineSpan)
    }) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp), color = Color.White)
    }
}

private fun LazyGridScope.error(
    message: String
) {
    item(span = {
        GridItemSpan(maxLineSpan)
    }) {
        Text(
            text = message,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}