package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(private val usersAgent: UsersAgent) {

    open fun loadUsers(): Single<Users> = usersAgent.loadUsers()
}


