package com.example.pierre.kotlinsample.users.model.localStorage

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.pierre.kotlinsample.users.model.User

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}