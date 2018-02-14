package com.example.pierre.kotlinsample.users.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Users(@SerializedName("users") val userList: List<User>)

@Entity(tableName = "users")
data class User (
        @ColumnInfo(name = "email") var email: String,
        @ColumnInfo(name = "name")  var name: String,
        @ColumnInfo(name = "infos")  var infos: String) {

    constructor() : this("", "", "")

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}