package com.themoviedb.model


import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.themoviedb.utils.StringUtils
import kotlinx.android.parcel.Parcelize
import java.util.*

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieSynopsisResponse(
    @Json(name = "adult")
    val adult: Boolean,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "budget")
    val budget: Int,
    @Json(name = "genres")
    val genres: List<Genre>,
    @Json(name = "homepage")
    val homepage: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "imdb_id")
    val imdbId: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "production_companies")
    var productionCompanies: List<ProductionCompany>,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountry>,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "revenue")
    val revenue: Long,
    @Json(name = "runtime")
    val runtime: Int,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    @Json(name = "status")
    val status: String,
    @Json(name = "tagline")
    val tagline: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "video")
    val video: Boolean,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int
) : Parcelable {

    @RequiresApi(Build.VERSION_CODES.N)
    fun getGenreString(): String? {
        val stringJoiner = StringJoiner(", ")
        repeat(genres.size) {
            stringJoiner.add(genres.get(it).name)
        }

        return stringJoiner.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLanguageString(): String? {
        val stringJoiner = StringJoiner(", ")
        repeat(spokenLanguages.size) {
            stringJoiner.add(spokenLanguages.get(it).englishName)
        }

        return stringJoiner.toString()
    }

    fun getRuntimeString(): String {
        val hour: Float = runtime.toFloat() / 60
        return String.format("%.2f", hour)
    }

    fun getBudgetString(): String {
        return if (budget != 0) StringUtils.getCurrency(budget.toFloat()) else "NA"
    }


    fun getRevenueString(): String {
        return if (revenue != 0L) StringUtils.getCurrency(revenue.toFloat()) else "NA"
    }

}


@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class Genre(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCompany(
    @Json(name = "id")
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin_country")
    val originCountry: String
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "name")
    val name: String
) : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "name")
    val name: String
) : Parcelable