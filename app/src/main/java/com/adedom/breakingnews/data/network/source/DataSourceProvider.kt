package com.adedom.breakingnews.data.network.source

import com.adedom.breakingnews.data.network.api.BreakingNewsApi
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class DataSourceProvider {

    fun getBreakingNews(): BreakingNewsApi {
        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)

            addInterceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("apiKey", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }

            addNetworkInterceptor(StethoInterceptor())
        }.build()

        val retrofit = Retrofit.Builder().apply {
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            baseUrl(BASE_URL)
        }.build()

        return retrofit.create()
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/"
        private const val API_KEY = "2f6d946ba27f46b3a0fcad9ddedf3bd6"
    }

}
