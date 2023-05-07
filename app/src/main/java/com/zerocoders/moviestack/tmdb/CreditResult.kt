package com.zerocoders.showdiary.tmdb

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditResult(
    @SerialName("id") val id: Int,
    @SerialName("cast") val cast: List<Cast> = emptyList(),
    @SerialName("crew") val crew: List<Crew> = emptyList()
)

@Serializable
data class Crew(
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("profile_path") val profilePath: String? = null,
    @SerialName("credit_id") val creditId: String,
    @SerialName("department") val department: String,
    @SerialName("job") val job: String
)

@Serializable
data class Cast(
    @SerialName("adult") val adult: Boolean = false,
    @SerialName("gender") val gender: Int,
    @SerialName("id") val id: Int,
    @SerialName("known_for_department") val knownForDepartment: String,
    @SerialName("name") val name: String,
    @SerialName("original_name") val originalName: String,
    @SerialName("popularity") val popularity: Float,
    @SerialName("profile_path") val profilePath: String?=null,
    @SerialName("cast_id") val castId: Int,
    @SerialName("character") val character: String,
    @SerialName("credit_id") val creditId: String,
    @SerialName("order") val order: Int = 0
)