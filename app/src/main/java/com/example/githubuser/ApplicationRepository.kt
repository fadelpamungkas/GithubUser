package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ApplicationRepository(private val dao: UserDAO) {
    val users: LiveData<List<User>> = dao.getAllUsers()
    var findUserDB: LiveData<User>? = null
    suspend fun insert(user: User) {
        dao.insert(user)
    }

    fun findUser(username: String) : LiveData<User>? {
        findUserDB = dao.findUser(username)
        return findUserDB
    }

    suspend fun delete(username: String) {
        dao.delete(username)
    }

}