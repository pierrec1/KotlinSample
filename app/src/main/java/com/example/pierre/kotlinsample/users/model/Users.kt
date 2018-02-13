package com.example.pierre.kotlinsample.users.model

import com.google.gson.annotations.SerializedName

data class Users(@SerializedName("users") val userList: List<User>)

data class User (
        var email: String,
        var name: String ,
        var infos: String
)