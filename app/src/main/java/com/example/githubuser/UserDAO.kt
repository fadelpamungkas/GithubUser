package com.example.githubuser

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("delete from user_favorite")
    fun deleteAllUsers()

    @Query("select * from user_favorite")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_favorite WHERE username LIKE :username")
    fun findUser(username: String): User
}