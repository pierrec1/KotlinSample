package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import javax.inject.Inject

class GetUsersAgent @Inject constructor(private val getUsersNetworkDataSource: GetUsersNetworkDataSource) {

    fun loadUsers(): Single<Users> = getUsersNetworkDataSource.getUserList()
}


