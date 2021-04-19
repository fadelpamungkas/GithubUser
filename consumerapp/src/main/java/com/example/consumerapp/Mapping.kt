package com.example.consumerapp

import android.database.Cursor

object Mapping {
    fun mapCursorToArrayList(cursor: Cursor?):ArrayList<User>{
        val list = ArrayList<User>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserFavorite.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserFavorite.AVATAR))
                list.add(
                    User(
                        username,
//                        id,
                        avatarUrl
                    )
                )
            }
        }
        return list
    }

}