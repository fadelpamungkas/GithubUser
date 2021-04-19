package com.example.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoriteViewModel (application: Application) : AndroidViewModel(application){

    private var listUsers = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUser(context: Context){
        val cursor = context.contentResolver.query(
            DatabaseContract.UserFavorite.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        listUsers.postValue(Mapping.mapCursorToArrayList(cursor))
    }

    fun getFavoriteUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

}