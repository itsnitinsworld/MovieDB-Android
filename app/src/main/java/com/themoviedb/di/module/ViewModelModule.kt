package com.themoviedb.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.themoviedb.di.scope.ViewModelKey
import com.themoviedb.ui.ViewModelFactory
import com.themoviedb.ui.fragment.intro.IntroScreenViewModel
import com.themoviedb.ui.fragment.moviedetail.MovieDetailViewModel
import com.themoviedb.ui.fragment.movielist.MovieListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author- Nitin Khanna
 * @date -
 */
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(IntroScreenViewModel::class)
    abstract fun bindIntroScreenViewModel(viewModel: IntroScreenViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel::class)
    abstract fun bindMovieListViewModel(viewModel: MovieListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailsViewModel(viewModel: MovieDetailViewModel): ViewModel
}