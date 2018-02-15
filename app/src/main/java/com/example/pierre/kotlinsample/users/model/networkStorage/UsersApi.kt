package com.example.pierre.kotlinsample.users.model.networkStorage

import com.example.pierre.kotlinsample.users.model.Users
import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("users.json")
    fun getUsers(): Single<Users>
}