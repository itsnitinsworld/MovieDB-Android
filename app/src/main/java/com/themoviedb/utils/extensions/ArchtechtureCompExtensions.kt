package com.themoviedb.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * @author- Nitin Khanna
 * @date - 21-11-20
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}
