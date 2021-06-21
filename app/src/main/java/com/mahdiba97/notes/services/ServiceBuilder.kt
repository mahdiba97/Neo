package com.mahdiba97.notes.services

import com.mahdiba97.notes.URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {
    companion object {
        //        logger
        private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        //        okhttp client
        private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

        private val builder = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

        private val retrofit = builder.build()

        fun <T> buildService(service: Class<T>): T {
            return retrofit.create(service)
        }
    }
}