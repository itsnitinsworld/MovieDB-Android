package com.themoviedb.base

import androidx.lifecycle.ViewModel
import com.themoviedb.network.APIClient
import com.themoviedb.network.APIInterface
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * @author- Nitin Khanna
 * @date -
 */
open class BaseViewModel : ViewModel() {
    var compositeDisposable: CompositeDisposable? = null

    val apiInterface = APIClient.getRetrofit().create(APIInterface::class.java)

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}