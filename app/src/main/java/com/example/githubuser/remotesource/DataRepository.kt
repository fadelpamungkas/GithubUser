package com.example.githubuser.remotesource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
