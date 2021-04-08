package com.example.githubuser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PostServices {

    companion object {
        const val TOKEN = "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"
    }

    @GET("/search/users")
    @Headers("Authorization: token $TOKEN")
    fun searchUser(@Query("q") key: String): Call<Result>

    @GET("/users/{username}")
    @Headers("Authorization: token $TOKEN")
    fun getUser(@Path("username") username: String?): Call<User>

    @GET("/users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    fun getFollowing(@Path("username") username: String?): Call<List<User>>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    fun getFollowers(@Path("username") username: String?): Call<List<User>>

}