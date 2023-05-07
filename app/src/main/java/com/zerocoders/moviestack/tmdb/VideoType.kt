package com.zerocoders.moviestack.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class VideoType(val value: String) {
    @SerialName("Trailer")
    TRAILER("Trailer"),

    @SerialName("Teaser")
    TEASER("Teaser"),

    @SerialName("Clip")
    CLIP("Clip"),

    @SerialName("Featurette")
    FEATURETTE("Featurette"),

    @SerialName("Bloopers")
    BLOOPERS("Bloopers"),

    @SerialName("Opening Credits")
    OPENING_CREDITS("Opening Credits"),

    @SerialName("Behind the Scenes")
    BEHIND_THE_SCENES("Behind the Scenes");
}