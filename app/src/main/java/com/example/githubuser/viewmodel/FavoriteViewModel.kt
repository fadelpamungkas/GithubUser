package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuser.db.AppDatabase
import com.example.githubuser.db.ApplicationRepository
import com.example.githubuser.model.User
import com.example.githubuser.db.UserDAO
import kotlinx.coroutines.launch

class FavoriteViewModel (application: Application) : AndroidViewModel(application){

    private val databaseUser: LiveData<List<User>>
    private val repository: ApplicationRepository
    private var findUserDB: LiveData<User>? = null

    init {
        val dao: UserDAO = AppDatabase.getDatabase(application.applicationContext).userDao()
        repository = ApplicationRepository(dao)
        databaseUser = repository.users
    }

    fun insert(user: User) = viewModelScope.launch { repository.insert(user) }

    fun delete(user: User) = viewModelScope.launch { repository.delete(user.username) }

    fun findUser(username: String) : LiveData<User>? {
        findUserDB = repository.findUser(username)
        return findUserDB
    }

    fun getAllUsers() : LiveData<List<User>> = databaseUser

}