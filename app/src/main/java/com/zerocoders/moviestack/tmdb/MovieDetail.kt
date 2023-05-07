package com.zerocoders.showdiary.tmdb

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val adult: Boolean,
    @SerialName("backdrop_path") val backdropPath: String?,
    val budget: Long,
    @SerialName("genres") val genres: List<Genre>,
    val homepage: String? = null,
    val id: Int,
    @SerialName("imdb_id") val imdbId: String? = null,
    val title: String,
    val runtime: Int? = null,
    @SerialName("original_title") val originalTitle: String,
    @SerialName("original_language") val originalLanguage: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("vote_average") override val voteAverage: Float,
    @SerialName("vote_count") override val voteCount: Int,
//    @SerialName("external_ids") val externalIds: ExternalIds? = null,
    val status: MovieStatus,
    val tagline: String,
    val video: Boolean,
    val popularity: Float,
    @SerialName("release_date") @Serializable(LocalDateSerializer::class) val releaseDate: LocalDate?,
    val revenue: Long,
//    @SerialName("release_dates") val releaseDates: Result<ReleaseDates>? = null,
//    @SerialName("production_companies") val productionCompanies: List<Company>? = null,
//    @SerialName("production_countries") val productionCountries: List<Country>? = null,
//    @SerialName("watch/providers") val watchProviders: ProviderResult? = null,
//    @SerialName("credits") val credits: Credits? = null,
//    @SerialName("videos") val videos: Result<Video>? = null,
//    @SerialName("images") val images: Images? = null,
) : RatingItem {

//    val posterImage get(): Image? = Image.poster(posterPath)
//    val backdropImage get(): Image? = Image.backdrop(backdropPath)
}