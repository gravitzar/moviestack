package com.zerocoders.showdiary.tmdb

import com.zerocoders.moviestack.tmdb.Video
import kotlinx.serialization.Serializable

@Serializable
data class VideoResult(
    val id: Int,
    val results: List<Video> = emptyList()
)
