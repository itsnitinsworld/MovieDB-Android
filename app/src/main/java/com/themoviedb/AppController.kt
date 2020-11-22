package com.themoviedb

import android.app.Application
import com.themoviedb.utils.PreferenceUtils
import com.themoviedb.utils.ToastUtils
import dagger.hilt.android.HiltAndroidApp

/**
 * @author Nitin Khanna
 * @date 19-11-2020
 */

@HiltAndroidApp
class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceUtils.init(this)
        ToastUtils.init(this)
    }

}