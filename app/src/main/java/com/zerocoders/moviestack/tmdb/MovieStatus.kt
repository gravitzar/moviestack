package com.zerocoders.showdiary.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class MovieStatus(val value: String) {
    @SerialName("Rumored")
    RUMORED("Rumored"),

    @SerialName("Planned")
    PLANNED("Planned"),

    @SerialName("In Production")
    IN_PRODUCTION("In Production"),

    @SerialName("Post Production")
    POST_PRODUCTION("Post Production"),

    @SerialName("Released")
    RELEASED("Released"),

    @SerialName("Canceled")
    CANCELED("Canceled");
}