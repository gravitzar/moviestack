package com.zerocoders.moviestack.model.video

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VideoSite(val value: String) {
    @SerialName("YouTube")
    YOUTUBE("YouTube"),

    @SerialName("Vimeo")
    VIMEO("Vimeo");
}