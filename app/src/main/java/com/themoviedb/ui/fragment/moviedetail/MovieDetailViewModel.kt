package com.themoviedb.ui.fragment.moviedetail

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.R
import com.themoviedb.base.BaseViewModel
import com.themoviedb.model.*
import com.themoviedb.network.APIInterface
import com.themoviedb.network.NetworkHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers

open class MovieDetailViewModel @ViewModelInject constructor(
    @ApplicationContext private var context: Context,
    private var apiInterface: APIInterface,
    private var networkHelper: NetworkHelper
) :
    BaseViewModel() {
    private val _movieDetailsModel = MutableLiveData<MovieDetailsModel>()
    val movieDetailsMode: LiveData<MovieDetailsModel> = _movieDetailsModel

    private val _isEmptyCastList = MutableLiveData<Boolean>()
    val isEmptyCastList: LiveData<Boolean> = _isEmptyCastList

    private val _isEmptySimilarMoviesList = MutableLiveData<Boolean>()
    val isEmptySimilarMoviesList: LiveData<Boolean> = _isEmptySimilarMoviesList


    private val _isEmptyReviewsList = MutableLiveData<Boolean>()
    val isEmptyReviewsList: LiveData<Boolean> = _isEmptyReviewsList

    private val _isEmptyProductionList = MutableLiveData<Boolean>()
    val isEmptyProductionList: LiveData<Boolean> = _isEmptyProductionList


    private val _isFinancialNegative = MutableLiveData<Boolean>()
    val isFinancialNegative: LiveData<Boolean> = _isFinancialNegative


    fun getDetails(movieId: String) {

        if (!networkHelper.isNetworkConnected()) {
            _onMessageError.value = context.getString(R.string.err_no_internet_connection)
            return
        }

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

                if (t == null) {
                    _isEmptyCastList.value = true
                    _isEmptyProductionList.value = true
                    _isEmptySimilarMoviesList.value = true
                    _isEmptyReviewsList.value = true
                    return
                }
                _isEmptyCastList.value = t.movieCast.isNullOrEmpty()
                _isEmptyReviewsList.value = t.movieReviews.isNullOrEmpty()
                _isEmptySimilarMoviesList.value = t.similarMoviesResult.isNullOrEmpty()
                _isEmptyProductionList.value =
                    t.movieSynopsis == null || t.movieSynopsis?.productionCompanies.isNullOrEmpty()

                _isFinancialNegative.value =
                    (t.movieSynopsis?.budget!!.toLong() - t.movieSynopsis?.revenue!!) > 0

            }

        }


        val movieDetails: Observable<MovieDetailsModel> = Observable.zip(
            apiInterface.movieDetails(movieId)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.movieCredits(movieId)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.movieReviews(movieId)!!
                .subscribeOn(Schedulers.io()),
            apiInterface.similarMovies(movieId)!!
                .subscribeOn(Schedulers.io()),
            Function4<MovieSynopsisResponse?, MovieCreditResponse?, MovieReviewResponse?, MovieListResponse?, MovieDetailsModel?> { movieDetails, movieCreditResponse, movieReviewResponse, similarMoviesResponse ->
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
