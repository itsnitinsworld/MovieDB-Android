package com.themoviedb.di.component

import com.themoviedb.AppController
import com.themoviedb.di.module.APIModule
import com.themoviedb.di.module.ActivityModuleBuilder
import com.themoviedb.di.module.FragmentModuleBuilder
import com.themoviedb.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


/**
 * @author- Nitin Khanna
 * @date - 21-11-20
 */

@Singleton
@Component(
    modules = [
        ActivityModuleBuilder::class,
        AndroidInjectionModule::class,
        FragmentModuleBuilder::class,
        APIModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun apiModule(apiModule: APIModule): Builder
    }

    fun inject(app: AppController)
}