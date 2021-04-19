package com.example.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    internal  class UserFavorite: BaseColumns {
        companion object{
            private const val AUTHORITY = "com.example.githubuser"
            private const val SCHEME = "content"
            private const val TABLE_NAME="user_favorite"
            const val USERNAME = "username"
            const val AVATAR = "avatar"

            val CONTENT_URI = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()!!
        }
    }
}