package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("delete from user_favorite")
    suspend fun deleteAllUsers()

    @Query("select * from user_favorite")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_favorite WHERE username LIKE :username")
    suspend fun findUser(username: String): User
}