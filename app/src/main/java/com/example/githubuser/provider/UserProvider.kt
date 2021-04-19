package com.example.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser.db.AppDatabase
import com.example.githubuser.db.UserDAO

class UserProvider : ContentProvider() {

    private lateinit var dao: UserDAO

    companion object {
        private const val AUTHORITY = "com.example.githubuser"
        private const val TABLE_NAME = "user_favorite"
        const val ID_FAVORITE = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE)
        }
    }

    override fun onCreate(): Boolean {
        dao = context?.let { context ->
            AppDatabase.getDatabase(context).userDao()
        }!!
        return false
    }

    override fun query(
            uri: Uri, projection: Array<String>?, selection: String?,
            selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val cursor : Cursor?
        when(uriMatcher.match(uri)){
            ID_FAVORITE -> {
                cursor = dao.getAllUsersCursor()
                if (context != null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
}