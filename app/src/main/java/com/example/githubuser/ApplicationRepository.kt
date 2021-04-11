package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ApplicationRepository(private val dao: UserDAO) {
    val users: LiveData<List<User>> = dao.getAllUsers()

    suspend fun insert(user: User) {
        dao.insert(user)
    }

    suspend fun findUser(username: String) {
        dao.findUser(username)
    }

    suspend fun delete(username: String) {
        dao.delete(username)
    }

}