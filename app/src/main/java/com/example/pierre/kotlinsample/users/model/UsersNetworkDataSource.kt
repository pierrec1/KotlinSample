package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class UsersNetworkDataSource @Inject constructor(retrofit: Retrofit) {

    private val usersApi: UsersApi = retrofit
            .create(UsersApi::class.java)

    fun getUserList(): Single<Users> {
        return usersApi.getUsers()
    }
}