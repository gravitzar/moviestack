package com.zerocoders.moviestack.model

import com.zerocoders.moviestack.model.common.AnyMedia
import com.zerocoders.moviestack.model.common.BackdropMedia
import com.zerocoders.moviestack.model.common.PosterMedia
import com.zerocoders.moviestack.model.common.RatingItem
import com.zerocoders.moviestack.network.serializer.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Polymorphic
@Serializable
sealed class MediaListItem : AnyMedia, BackdropMedia, PosterMedia, RatingItem {
    abstract val overview: String
    abstract val genresIds: List<Int>
    abstract val popularity: Float
    abstract val originalLanguage: String
}

@Serializable
@SerialName("movie")
data class Movie(
    @SerialName("poster_path") override val posterPath: String?,
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("overview") override val overview: String,
    @SerialName("release_date") @Serializable(LocalDateSerializer::class) val releaseDate: LocalDate? = null,
    @SerialName("genre_ids") override val genresIds: List<Int> = emptyList(),
    @SerialName("genres") val genres: List<Genre> = emptyList(),
    @SerialName("id") override val id: Int,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("original_language") override val originalLanguage: String,
    @SerialName("title") val title: String,
    @SerialName("backdrop_path") override val backdropPath: String?,
    @SerialName("popularity") override val popularity: Float,
    @SerialName("vote_count") override val voteCount: Int,
    @SerialName("video") val video: Boolean,
    @SerialName("vote_average") override val voteAverage: Float,
    @SerialName("runtime") val runtime: Int? = null,
) : MediaListItem()