package com.themoviedb.network

import com.themoviedb.ui.fragment.movielist.model.MovieListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {
    @GET("movie/now_playing")
    fun moviesList(@Query("api_key")api_key:String): Single<MovieListResponse>?

}
