package com.zerocoders.moviestack.model.video

import com.zerocoders.moviestack.model.common.AnyMedia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResult(
    @SerialName("id") override val id: Int,
    val results: List<Video> = emptyList()
) : AnyMedia
