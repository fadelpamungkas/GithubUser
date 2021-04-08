package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ApplicationRepository(private val dao: UserDAO) {
    val users: LiveData<List<User>> = dao.getAllUsers()

    suspend fun insert(user: User) {
        dao.insert(user)
    }

    suspend fun update(user: User) {
        dao.update(user)
    }

    suspend fun findUser(user: String) {
        dao.findUser(user)
    }

    suspend fun delete(user: User) {
        dao.delete(user)
    }

    suspend fun deleteAll() {
        dao.deleteAllUsers()
    }

}