package com.themoviedb.model


import android.annotation.SuppressLint
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieCreditResponse(
    @Json(name = "cast")
    val movieCast: List<MovieCast>,
    @Json(name = "crew")
    val movieCrew: List<MovieCrew>,
    @Json(name = "id")
    val id: Int
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieCrew(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "department")
    val department: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "job")
    val job: String,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieCast(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "cast_id")
    val castId: Int,
    @Json(name = "character")
    val character: String,
    @Json(name = "credit_id")
    val creditId: String,
    @Json(name = "gender")
    val gender: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "known_for_department")
    val knownForDepartment: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "original_name")
    val originalName: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "profile_path")
    val profilePath: String?
) : Parcelable