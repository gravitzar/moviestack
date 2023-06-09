package com.zerocoders.moviestack.model

import com.zerocoders.moviestack.model.common.AnyMedia
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id") override val id: Int,
    val name: String,
) : AnyMedia