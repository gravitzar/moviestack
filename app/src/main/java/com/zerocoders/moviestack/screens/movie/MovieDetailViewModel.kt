package com.zerocoders.moviestack.screens.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zerocoders.moviestack.model.Cast
import com.zerocoders.moviestack.model.Crew
import com.zerocoders.moviestack.model.Movie
import com.zerocoders.moviestack.model.video.Video
import com.zerocoders.moviestack.model.video.VideoSite
import com.zerocoders.moviestack.model.video.VideoType
import com.zerocoders.moviestack.network.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.stream.StreamInfo
import javax.inject.Inject
import kotlin.random.Random

sealed class TrailerState {
    object Loading : TrailerState()
    object Error : TrailerState()
    object Success : TrailerState()
    object Completed : TrailerState()
}

data class MovieDetailScreenUiState(
    val movie: Movie? = null,
    val movieVideos: List<Video> = emptyList(),
    val movieCasts: List<Cast> = emptyList(),
    val movieCrews: List<Crew> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val isFavourite: Boolean = Random.nextBoolean(),
    val isBookMarked: Boolean = Random.nextBoolean(),
    val trailerVideoState: TrailerState = TrailerState.Loading,
    val trailerVideoLink: String? = null
)

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val tmdbRemoteDataSource: RemoteDataSource,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MovieDetailScreenUiState())
    val uiState: StateFlow<MovieDetailScreenUiState> = _uiState.asStateFlow()

    fun updateBookMark() = viewModelScope.launch {
        _uiState.update {
            it.copy(isBookMarked = !it.isBookMarked)
        }
    }

    fun updateFavourite() = viewModelScope.launch {
        _uiState.update {
            it.copy(isFavourite = !it.isFavourite)
        }
    }

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _uiState.update { uiState ->
                uiState.copy(
                    isLoading = true,
                    isSuccess = false,
                    isError = false
                )
            }
            try {
                val movieResponse = tmdbRemoteDataSource.getMovie(movieId)
                if (movieResponse != null) {
                    _uiState.update { uiState: MovieDetailScreenUiState ->
                        uiState.copy(
                            movie = movieResponse,
                            isLoading = false,
                            isSuccess = true,
                            isError = false
                        )
                    }
                } else {
                    _uiState.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            isSuccess = false,
                            isError = true
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true
                    )
                }
            }
            try {
                tmdbRemoteDataSource.getMovieVideos(movieId)?.let {
                    _uiState.update { uiState: MovieDetailScreenUiState ->
                        uiState.copy(
                            movieVideos = it.results
                        )
                    }
                    fetchAndExtractVideoData()
                }
            } catch (_: Exception) {
            }
            try {
                tmdbRemoteDataSource.getMovieCredits(movieId)?.let {
                    _uiState.update { uiState: MovieDetailScreenUiState ->
                        uiState.copy(
                            movieCasts = it.cast,
                            movieCrews = it.crew
                        )
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun fetchAndExtractVideoData() {
        val video = _uiState.value.movieVideos.filter { it.site == VideoSite.YOUTUBE }.firstOrNull { it.type == VideoType.TRAILER }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = "https://www.youtube.com/watch?v=${video?.key}"
                val service = NewPipe.getServiceByUrl(url)
                val serviceId = service.serviceId
                val streamInfo = StreamInfo.getInfo(NewPipe.getService(serviceId), url)
                _uiState.update {
                    it.copy(
                        trailerVideoState = TrailerState.Success,
                        trailerVideoLink = streamInfo.videoStreams.lastOrNull()?.content
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.update {
                    it.copy(trailerVideoState = TrailerState.Error)
                }
            }
        }
    }
}