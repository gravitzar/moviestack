package com.zerocoders.showdiary.tmdb.onlineconverted

//import com.fasterxml.jackson.annotation.JsonProperty
//
//data class Root(
//    val adult: Boolean,
//    @JsonProperty("backdrop_path")
//    val backdropPath: String,
//    @JsonProperty("belongs_to_collection")
//    val belongsToCollection: Any?,
//    val budget: Long,
//    val genres: List<Genre>,
//    val homepage: String,
//    val id: Long,
//    @JsonProperty("imdb_id")
//    val imdbId: String,
//    @JsonProperty("original_language")
//    val originalLanguage: String,
//    @JsonProperty("original_title")
//    val originalTitle: String,
//    val overview: String,
//    val popularity: Double,
//    @JsonProperty("poster_path")
//    val posterPath: Any?,
//    @JsonProperty("production_companies")
//    val productionCompanies: List<ProductionCompany>,
//    @JsonProperty("production_countries")
//    val productionCountries: List<ProductionCountry>,
//    @JsonProperty("release_date")
//    val releaseDate: String,
//    val revenue: Long,
//    val runtime: Long,
//    @JsonProperty("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>,
//    val status: String,
//    val tagline: String,
//    val title: String,
//    val video: Boolean,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//)
//
//data class Genre(
//    val id: Long,
//    val name: String,
//)
//
//data class ProductionCompany(
//    val id: Long,
//    @JsonProperty("logo_path")
//    val logoPath: String?,
//    val name: String,
//    @JsonProperty("origin_country")
//    val originCountry: String,
//)
//
//data class ProductionCountry(
//    @JsonProperty("iso_3166_1")
//    val iso31661: String,
//    val name: String,
//)
//
//data class SpokenLanguage(
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    val name: String,
//)
//
//data class Root(
//    val page: Long,
//    val results: List<Result>,
//    @JsonProperty("total_pages")
//    val totalPages: Long,
//    @JsonProperty("total_results")
//    val totalResults: Long,
//)
//
//data class Result(
//    val adult: Boolean,
//    @JsonProperty("backdrop_path")
//    val backdropPath: Any?,
//    @JsonProperty("genre_ids")
//    val genreIds: List<Long>,
//    val id: Long,
//    @JsonProperty("original_language")
//    val originalLanguage: String,
//    @JsonProperty("original_title")
//    val originalTitle: String,
//    val overview: String,
//    @JsonProperty("release_date")
//    val releaseDate: String,
//    @JsonProperty("poster_path")
//    val posterPath: Any?,
//    val popularity: Double,
//    val title: String,
//    val video: Boolean,
//    @JsonProperty("vote_average")
//    val voteAverage: Long,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//)
//
//data class Root(
//    val id: Long,
//    val results: List<Result>,
//)
//
//data class Result(
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    @JsonProperty("iso_3166_1")
//    val iso31661: String,
//    val name: String,
//    val key: String,
//    val site: String,
//    val size: Long,
//    val type: String,
//    val official: Boolean,
//    @JsonProperty("published_at")
//    val publishedAt: String,
//    val id: String,
//)
//
//data class Root(
//    val page: Long,
//    val results: List<Result>,
//    val dates: Dates,
//    @JsonProperty("total_pages")
//    val totalPages: Long,
//    @JsonProperty("total_results")
//    val totalResults: Long,
//)
//
//data class Result(
//    @JsonProperty("poster_path")
//    val posterPath: String,
//    val adult: Boolean,
//    val overview: String,
//    @JsonProperty("release_date")
//    val releaseDate: String,
//    @JsonProperty("genre_ids")
//    val genreIds: List<Long>,
//    val id: Long,
//    @JsonProperty("original_title")
//    val originalTitle: String,
//    @JsonProperty("original_language")
//    val originalLanguage: String,
//    val title: String,
//    @JsonProperty("backdrop_path")
//    val backdropPath: String,
//    val popularity: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//    val video: Boolean,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//)
//
//data class Dates(
//    val maximum: String,
//    val minimum: String,
//)
//
//data class Root(
//    val id: Long,
//    val results: List<Result>,
//)
//
//data class Result(
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    @JsonProperty("iso_3166_1")
//    val iso31661: String,
//    val name: String,
//    val key: String,
//    val site: String,
//    val size: Long,
//    val type: String,
//    val official: Boolean,
//    @JsonProperty("published_at")
//    val publishedAt: String,
//    val id: String,
//)
//
//data class Root(
//    val adult: Boolean,
//    @JsonProperty("backdrop_path")
//    val backdropPath: String,
//    @JsonProperty("belongs_to_collection")
//    val belongsToCollection: Any?,
//    val budget: Long,
//    val genres: List<Genre>,
//    val homepage: String,
//    val id: Long,
//    @JsonProperty("imdb_id")
//    val imdbId: String,
//    @JsonProperty("original_language")
//    val originalLanguage: String,
//    @JsonProperty("original_title")
//    val originalTitle: String,
//    val overview: String,
//    val popularity: Double,
//    @JsonProperty("poster_path")
//    val posterPath: String,
//    @JsonProperty("production_companies")
//    val productionCompanies: List<ProductionCompany>,
//    @JsonProperty("production_countries")
//    val productionCountries: List<ProductionCountry>,
//    @JsonProperty("release_date")
//    val releaseDate: String,
//    val revenue: Long,
//    val runtime: Long,
//    @JsonProperty("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>,
//    val status: String,
//    val tagline: String,
//    val title: String,
//    val video: Boolean,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//    val videos: Videos,
//    val images: Images,
//)

//data class Genre(
//    val id: Long,
//    val name: String,
//)
//
//data class ProductionCompany(
//    val id: Long,
//    @JsonProperty("logo_path")
//    val logoPath: String?,
//    val name: String,
//    @JsonProperty("origin_country")
//    val originCountry: String,
//)
//
//data class ProductionCountry(
//    @JsonProperty("iso_3166_1")
//    val iso31661: String,
//    val name: String,
//)
//
//data class SpokenLanguage(
//    @JsonProperty("english_name")
//    val englishName: String,
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    val name: String,
//)
//
//data class Videos(
//    val results: List<Result>,
//)
//
//data class Result(
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    @JsonProperty("iso_3166_1")
//    val iso31661: String,
//    val name: String,
//    val key: String,
//    @JsonProperty("published_at")
//    val publishedAt: String,
//    val site: String,
//    val size: Long,
//    val type: String,
//    val official: Boolean,
//    val id: String,
//)
//
//data class Images(
//    val backdrops: List<Backdrop>,
//    val logos: List<Logo>,
//    val posters: List<Poster>,
//)
//
//data class Backdrop(
//    @JsonProperty("aspect_ratio")
//    val aspectRatio: Double,
//    val height: Long,
//    @JsonProperty("iso_639_1")
//    val iso6391: String?,
//    @JsonProperty("file_path")
//    val filePath: String,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//    val width: Long,
//)
//
//data class Logo(
//    @JsonProperty("aspect_ratio")
//    val aspectRatio: Double,
//    val height: Long,
//    @JsonProperty("iso_639_1")
//    val iso6391: String,
//    @JsonProperty("file_path")
//    val filePath: String,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//    val width: Long,
//)
//
//data class Poster(
//    @JsonProperty("aspect_ratio")
//    val aspectRatio: Double,
//    val height: Long,
//    @JsonProperty("iso_639_1")
//    val iso6391: String?,
//    @JsonProperty("file_path")
//    val filePath: String,
//    @JsonProperty("vote_average")
//    val voteAverage: Double,
//    @JsonProperty("vote_count")
//    val voteCount: Long,
//    val width: Long,
//)
//
//
////https://image.tmdb.org/t/p/w500/wwemzKWzjKYJFfCeiB57q3r4Bcm.png
////https://image.tmdb.org/t/p/original/wwemzKWzjKYJFfCeiB57q3r4Bcm.png
////https://image.tmdb.org/t/p/original/wwemzKWzjKYJFfCeiB57q3r4Bcm.svg