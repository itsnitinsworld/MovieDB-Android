package com.themoviedb.ui.fragment.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel
import com.themoviedb.ui.fragment.movielist.model.MovieResults
import com.themoviedb.utils.AppConstants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieListViewModel : BaseViewModel() {
    private var _moviesList = MutableLiveData<List<MovieResults>>().apply { value = emptyList() }
    var moviesList: LiveData<List<MovieResults>> = _moviesList

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError


    init {
        loadMovies()
    }


    private fun loadMovies() {

        _isViewLoading.value = true
        val observer: SingleObserver<List<MovieResults>?> = object :
            SingleObserver<List<MovieResults>?> {
            override fun onSubscribe(d: Disposable?) {
                compositeDisposable?.add(d)
            }

            override fun onError(e: Throwable?) {
                _isViewLoading.value = false
                _onMessageError.value = e?.localizedMessage
            }

            override fun onSuccess(t: List<MovieResults>?) {
                _isViewLoading.value = false
                _moviesList.value = t ?: emptyList()
            }

        }

        apiInterface.moviesList(AppConstants.MovieDB.API_KEY)!!
            .map {
                it.results
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
