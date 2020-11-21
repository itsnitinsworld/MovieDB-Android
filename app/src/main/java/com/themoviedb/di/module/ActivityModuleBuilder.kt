package com.themoviedb.di.module

import com.themoviedb.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author- Nitin Khanna
 * @date - 21-11-20
 */
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}