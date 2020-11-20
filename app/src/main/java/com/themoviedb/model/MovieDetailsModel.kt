package com.themoviedb.model

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
data class MovieDetailsModel(
    var movieSynopsis: MovieSynopsisResponse?,
    var movieReviews: List<MovieReviewResult>,
    var similarMoviesResult: List<MovieResults>,
    var movieCast: List<MovieCast>
)