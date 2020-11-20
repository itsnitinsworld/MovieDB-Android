package com.themoviedb.ui.fragment.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel
import com.themoviedb.model.*
import com.themoviedb.utils.AppConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel : BaseViewModel() {
    private val _movieDetailsModel = MutableLiveData<MovieDetailsModel>()
    val movieDetailsMode: LiveData<MovieDetailsModel> = _movieDetailsModel

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    fun getDetails(movieId: String) {

        _isViewLoading.value = true
        val observer: Observer<MovieDetailsModel?> = object :
            Observer<MovieDetailsModel?> {
            override fun onSubscribe(d: Disposable?) {
                compositeDisposable?.add(d)
            }

            override fun onError(e: Throwable?) {
                _isViewLoading.value = false
                _onMessageError.value = e?.localizedMessage
            }

            override fun onComplete() {
                _isViewLoading.value = false
            }

            override fun onNext(t: MovieDetailsModel?) {
                _movieDetailsModel.value = t
            }

        }


        val movieDetails: Observable<MovieDetailsModel> = Observable.zip(
            apiInterface.movieDetails(movieId, AppConstants.MovieDB.API_KEY)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.movieCredits(movieId, AppConstants.MovieDB.API_KEY)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.movieReviews(movieId, AppConstants.MovieDB.API_KEY)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.similarMovies(movieId, AppConstants.MovieDB.API_KEY)!!
                .subscribeOn(Schedulers.io()),
            Function4<MovieSynopsisResponse?, MovieCreditResponse?, MovieReviewResponse?, SimilarMoviesResponse?, MovieDetailsModel?> { movieDetails, movieCreditResponse, movieReviewResponse, similarMoviesResponse ->


                movieDetails?.productionCompanies =
                    (movieDetails?.productionCompanies?.filter { !it.logoPath.isNullOrEmpty() })
                        ?: emptyList()

                MovieDetailsModel(
                    movieSynopsis = movieDetails,
                    movieCast = (movieCreditResponse?.movieCast
                        ?: emptyList()).filter { !it.profilePath.isNullOrEmpty() },
                    movieReviews = (movieReviewResponse?.movieReviewResults ?: emptyList()),
                    similarMoviesResult = (similarMoviesResponse?.results ?: emptyList())
                )
            })



        movieDetails
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(observer)
    }
}
