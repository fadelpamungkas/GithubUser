package com.example.githubuser

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object DataRepository {

    fun create(): PostServices {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .client(OkHttpClient().newBuilder().build())
                .build()
        return retrofit.create(PostServices::class.java)
    }
}
