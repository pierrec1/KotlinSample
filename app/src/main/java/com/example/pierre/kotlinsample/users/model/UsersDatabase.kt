package com.example.pierre.kotlinsample.users.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1, exportSchema = false)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}