package com.zerocoders.moviestack.tmdb

import kotlinx.serialization.Serializable

@Serializable
data class VideoResult(
    val id: Int,
    val results: List<Video> = emptyList()
)
