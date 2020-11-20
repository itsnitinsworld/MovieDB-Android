package com.themoviedb

import android.app.Application
import com.themoviedb.utils.PreferenceUtils

/**
 * @author Nitin Khanna
 * @date 19-11-2020
 */

class AppController : Application() {
    companion object {

        private var instance: AppController? = null
        fun getInstance(): AppController {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferenceUtils.init(this)
    }


}