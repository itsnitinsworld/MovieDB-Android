package com.themoviedb.di.module

import com.themoviedb.ui.ViewModelFactory
import com.themoviedb.ui.fragment.intro.IntroScreenFragment
import com.themoviedb.ui.fragment.intro.IntroScreenViewModel
import com.themoviedb.ui.fragment.moviedetail.MovieDetailFragment
import com.themoviedb.ui.fragment.moviedetail.MovieDetailViewModel
import com.themoviedb.ui.fragment.movielist.MovieListFragment
import com.themoviedb.ui.fragment.movielist.MovieListViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

/**
 * @author- Nitin Khanna
 * @date - 21-11-20
 */
@Module
abstract class FragmentModuleBuilder {
    @ContributesAndroidInjector()
    abstract fun contributeMovieListFragment(): MovieListFragment

    @ContributesAndroidInjector()
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @ContributesAndroidInjector()
    abstract fun contributeIntroScreenFragment(): IntroScreenFragment

    /* Module that uses bound fragment and provided factory uses ViewModelProviders
        to provide instance of FeatureViewModel */
    @Module
    class InjectViewModel {

        @Provides
        fun provideMovieListViewModel(
            factory: ViewModelFactory,
            target: MovieListViewModel
        ) = factory.create(target::class.java)

        @Provides
        fun provideMovieDetailsViewModel(
            factory: ViewModelFactory,
            target: MovieDetailViewModel
        ) = factory.create(target::class.java)

        @Provides
        fun provideIntroScreenViewModel(
            factory: ViewModelFactory,
            target: IntroScreenViewModel
        ) = factory.create(target::class.java)

    }

}