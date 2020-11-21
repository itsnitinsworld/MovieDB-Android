package com.themoviedb.ui.fragment.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.base.BaseViewModel
import com.themoviedb.model.MovieResults
import com.themoviedb.network.APIInterface
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private var apiInterface: APIInterface) :
    BaseViewModel() {
    private var _moviesList = MutableLiveData<List<MovieResults>>().apply { value = emptyList() }
    var moviesList: LiveData<List<MovieResults>> = _moviesList

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


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
                if (t.isNullOrEmpty()) {
                    _isEmptyList.value = true
                    return
                }
                _isEmptyList.value = false
                _moviesList.value = t
            }

        }

        apiInterface.moviesList!!
            .map {
                it.results
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }
}
