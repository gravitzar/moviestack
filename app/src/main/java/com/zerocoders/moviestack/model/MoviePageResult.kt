package com.zerocoders.moviestack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePageResult(
    @SerialName("page") override val page: Int,
    @SerialName("results") override val results: List<Movie> = emptyList(),
    @SerialName("total_results") override val totalResults: Int,
    @SerialName("total_pages") override val totalPages: Int,
) : PageResult<Movie>