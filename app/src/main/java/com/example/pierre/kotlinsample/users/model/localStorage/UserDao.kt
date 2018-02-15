package com.example.pierre.kotlinsample.users.model.localStorage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.pierre.kotlinsample.users.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Query("select * from users WHERE " +
            "UPPER(name) LIKE UPPER('%' || :arg0 || '%') OR " +
            "UPPER(email) LIKE UPPER('%' || :arg0 || '%')")
    fun findByName(textToMatch: String): List<User>

    @Insert
    fun insert(user: User)

    @Query("DELETE FROM users")
    fun deleteAll()
}