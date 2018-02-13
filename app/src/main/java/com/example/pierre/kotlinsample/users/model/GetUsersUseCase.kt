package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(private val getUsersAgent: GetUsersAgent) {

    open fun loadUsers(): Single<Users> = getUsersAgent.loadUsers()
}


