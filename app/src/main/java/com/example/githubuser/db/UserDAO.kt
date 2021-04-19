package com.example.githubuser.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.model.User

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(user: User)

    @Query("delete from user_favorite where username = :username")
    suspend fun delete(username: String)

    @Query("select * from user_favorite")
    fun getAllUsers(): LiveData<List<User>>

    @Query("select * from user_favorite where username = :username")
    fun findUser(username: String) : LiveData<User>

    @Query ("SELECT * FROM user_favorite")
    fun getAllUsersCursor() : Cursor
}