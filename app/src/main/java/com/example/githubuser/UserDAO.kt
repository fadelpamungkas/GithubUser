package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User)

    @Query("delete from user_favorite where username = :username")
    suspend fun delete(username: String)

    @Query("select * from user_favorite")
    fun getAllUsers(): LiveData<List<User>>

    @Query("select * from user_favorite where username = :username")
    suspend fun findUser(username: String): User
}