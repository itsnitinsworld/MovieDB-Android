package com.themoviedb.network

import com.themoviedb.model.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIInterface {
    @GET("now_playing")
    fun moviesList(@Query("api_key") apiKey: String): Single<MovieListResponse>?

    @GET("{movie_id}")
    fun movieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Observable<MovieSynopsisResponse>?

    @GET("{movie_id}/reviews")
    fun movieReviews(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Observable<MovieReviewResponse>?

    @GET("{movie_id}/credits")
    fun movieCredits(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Observable<MovieCreditResponse>?

    @GET("{movie_id}/similar")
    fun similarMovies(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Observable<SimilarMoviesResponse>?

}
