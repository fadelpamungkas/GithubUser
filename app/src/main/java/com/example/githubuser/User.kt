package com.example.githubuser

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class Result(
    @SerializedName("items")
    val result: ArrayList<User>
)

@Parcelize
@Entity(tableName = "user_favorite")
data class User (
    @SerializedName("login")
    @PrimaryKey
    @NonNull
    val username: String,
    @SerializedName("type")
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String,
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("public_repos")
    var repository: String? = null,
    @SerializedName("company")
    var company: String? = null,
    @SerializedName("followers")
    var followers: String? = null,
    @SerializedName("following")
    var following: String? = null,
    @SerializedName("followers_url")
    var followers_url: String? = null,
    @SerializedName("following_url")
    var following_url: String? = null
) : Parcelable