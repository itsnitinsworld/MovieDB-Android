package com.themoviedb.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author- Nitin Khanna
 * @date - 20-11-2020
 */
abstract class BaseViewModel : ViewModel() {
    protected var compositeDisposable: CompositeDisposable? = null

    protected val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    protected val _onMessageError = MutableLiveData<String>()
    val onMessageError: LiveData<String> = _onMessageError

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}