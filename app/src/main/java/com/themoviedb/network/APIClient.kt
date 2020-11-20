package com.themoviedb.network

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 * @author- Nitin Khanna
 * @date -
 */
class APIClient {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        private var instance: Retrofit? = null

        fun getRetrofit(): Retrofit {
            if (instance == null) {
                synchronized(APIClient::class.java) {
                    if (instance == null) {
                        initializeAPIClient()
                    }
                }
            }
            return instance!!
        }


        private fun initializeAPIClient() {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            //TODO: check, Why this is not working?
            val networkInterceptor = Interceptor { chain ->
                var request = chain.request()
                val url =
                    request.url.newBuilder()
                        .addQueryParameter("api_key", "4c065e562519ef8755b6f816a9b81e67").build()
                request = request.newBuilder().url(url).build()
                chain.proceed(request)
            }
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(networkInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()


            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

        }
    }
}