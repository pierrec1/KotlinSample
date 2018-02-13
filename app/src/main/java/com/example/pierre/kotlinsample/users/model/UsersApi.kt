package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import retrofit2.http.GET

interface UsersApi {

    @GET("users.json")
    fun getUsers(): Single<Users>
}