package com.themoviedb.network

import com.themoviedb.model.MovieCreditResponse
import com.themoviedb.model.MovieListResponse
import com.themoviedb.model.MovieReviewResponse
import com.themoviedb.model.MovieSynopsisResponse
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path


interface APIInterface {
    @get:GET("now_playing")
    val moviesList: Single<MovieListResponse>?

    @GET("{movie_id}")
    fun movieDetails(
        @Path("movie_id") movieId: String
    ): Observable<MovieSynopsisResponse>?

    @GET("{movie_id}/reviews")
    fun movieReviews(
        @Path("movie_id") movieId: String
    ): Observable<MovieReviewResponse>?

    @GET("{movie_id}/credits")
    fun movieCredits(
        @Path("movie_id") movieId: String
    ): Observable<MovieCreditResponse>?

    @GET("{movie_id}/similar")
    fun similarMovies(
        @Path("movie_id") movieId: String
    ): Observable<MovieListResponse>?

}
