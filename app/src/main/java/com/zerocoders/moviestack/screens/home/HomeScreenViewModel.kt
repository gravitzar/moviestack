package com.zerocoders.moviestack.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerocoders.moviestack.SessionPreferenceManager
import com.zerocoders.moviestack.network.RemoteDataSource
import com.zerocoders.moviestack.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class HomeScreenUiState(
    val latestMovie: Movie? = null,
    val topRatedMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val upComingMovies: List<Movie> = emptyList(),
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val tmdbRemoteDataSource: RemoteDataSource,
    private val sessionPreferenceManager: SessionPreferenceManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    val isLoggedIn = sessionPreferenceManager.isLoggedInFlow

    fun getMovies() {
        viewModelScope.launch {
            tmdbRemoteDataSource.getMovie(436270)?.let {
                _uiState.update { uiState: HomeScreenUiState ->
                    uiState.copy(latestMovie = it)
                }
            }
            tmdbRemoteDataSource.getTopRatedMovies(Random.nextInt(1, 10))?.let {
                _uiState.update { uiState: HomeScreenUiState ->
                    uiState.copy(topRatedMovies = it.results)
                }
            }
            tmdbRemoteDataSource.getPopularMovies(Random.nextInt(1, 10))?.let {
                _uiState.update { uiState: HomeScreenUiState ->
                    uiState.copy(popularMovies = it.results)
                }
            }
            tmdbRemoteDataSource.getUpComingMovies(Random.nextInt(1, 10))?.let {
                _uiState.update { uiState: HomeScreenUiState ->
                    uiState.copy(upComingMovies = it.results)
                }
            }
            tmdbRemoteDataSource.getNowPlayingMovies(Random.nextInt(1, 10))?.let {
                _uiState.update { uiState: HomeScreenUiState ->
                    uiState.copy(nowPlayingMovies = it.results)
                }
            }
        }
    }
}