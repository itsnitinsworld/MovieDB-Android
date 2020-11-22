package com.themoviedb.ui.fragment.movielist

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.themoviedb.R
import com.themoviedb.base.BaseViewModel
import com.themoviedb.model.MovieResults
import com.themoviedb.network.APIInterface
import com.themoviedb.network.NetworkHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieListViewModel @ViewModelInject constructor(
    @ApplicationContext private var context: Context,
    private var apiInterface: APIInterface,
    private var networkHelper: NetworkHelper
) :
    BaseViewModel() {
    private var _moviesList = MutableLiveData<List<MovieResults>>().apply { value = emptyList() }
    var moviesList: LiveData<List<MovieResults>> = _moviesList

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    init {
        loadMovies()
    }

    private fun loadMovies() {
        if (!networkHelper.isNetworkConnected()) {
            _onMessageError.value = context.getString(R.string.err_no_internet_connection)
            return
        }
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
