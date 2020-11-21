package com.themoviedb.di.module

import com.themoviedb.BuildConfig
import com.themoviedb.network.APIInterface
import com.themoviedb.utils.AppConstants
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author- Nitin Khanna
 * @date - 21-11-20
 */

@Module
class APIModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideNetworkingInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val url =
                request.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.MOVIEDB_API_KEY).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.API.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }
}