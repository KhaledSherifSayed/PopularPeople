package com.meslmawy.ibtkarchallenge.domain.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class AllPeopleResponse(
    val page: Int? = null,
    val total_results: Int? = null,
    val total_pages: Int? = null,
    val results: List<People>? = null
)

@Parcelize
data class People(
    val id: Int,
    val name: String? = null,
    val popularity: Double? = null,
    val known_for_department: String? = null,
    val gender: Int? = null,
    val profile_path: String? = null,
    val adult: Boolean? = null,
    val description: String? = null,
    val known_for: List<Movies>? = null
) : Parcelable

@Parcelize
data class Movies(
    val original_name: String? = null,
    val vote_count: Int? = null,
    val poster_path: String? = null,
    val media_type: String? = null,
    val name: String? = null,
    val vote_average: Double? = null,
    val id: Int? = null,
    val first_air_date: String? = null,
    val original_language: String? = null,
    val genre_ids: List<Int>? = null,
    val backdrop_path: String? = null,
    val overview: String? = null,
    val origin_country: List<String>? = null
) : Parcelable


@Parcelize
data class ActorDetails(
    val birthday: String? = null,
    val known_for_department: String? = null,
    val deathday: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val also_known_as: List<String>? = null,
    val gender: Int? = null,
    val biography: String? = null,
    val popularity: Double? = null,
    val place_of_birth: String? = null,
    val profile_path: String? = null,
    val adult: Boolean? = null,
    val imdb_id: String? = null,
    val homepage: String? = null
) : Parcelable
