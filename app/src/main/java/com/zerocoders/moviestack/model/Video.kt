package com.zerocoders.moviestack.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("id") val id: String,
    @SerialName("iso_639_1") val iso639: String? = null,
    @SerialName("iso_3166_1") val iso3166: String? = null,
    @SerialName("key") val key: String,
    @SerialName("site") val site: VideoSite? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("size") val size: Int? = null, // 360, 480, 720, 1080
    @SerialName("type") val type: VideoType? = null,
) {
    var youtubeLink: String? = null
}