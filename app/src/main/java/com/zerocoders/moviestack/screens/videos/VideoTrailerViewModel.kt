package com.zerocoders.moviestack.screens.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class VideoTrailerScreenState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val videoUrl: String? = null
)

@HiltViewModel
class VideoTrailerViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(VideoTrailerScreenState())
    val uiState: StateFlow<VideoTrailerScreenState> = _uiState.asStateFlow()

    fun fetchAndExtractVideoData(videoId: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val url = "https://www.youtube.com/watch?v=${videoId}"
                val service = NewPipe.getServiceByUrl(url)
                val serviceId = service.serviceId
                val streamInfo = StreamInfo.getInfo(NewPipe.getService(serviceId), url)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        isError = false,
                        videoUrl = streamInfo.videoStreams.lastOrNull()?.content
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                        isError = true,
                        videoUrl = null
                    )
                }
            }
        }
    }
}