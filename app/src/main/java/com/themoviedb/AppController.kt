package com.themoviedb

import android.app.Application
import android.content.Context
import com.themoviedb.di.component.DaggerAppComponent
import com.themoviedb.di.module.APIModule
import com.themoviedb.utils.PreferenceUtils
import com.themoviedb.utils.ToastUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * @author Nitin Khanna
 * @date 19-11-2020
 */

class AppController : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        PreferenceUtils.init(context)
        ToastUtils.init(context)
        DaggerAppComponent.builder()
            .apiModule(APIModule())
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}