package com.fintech.denispok.fintechproject.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider private constructor() {

    companion object {
        @Volatile
        private var instance: Retrofit? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: Retrofit.Builder()
                            .baseUrl("https://fintech.tinkoff.ru/")
                            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .also { instance = it }
                }
    }
}
