package com.example.pierre.kotlinsample.users.model

import io.reactivex.Observable
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(private val usersAgent: UsersAgent) {

    open fun loadUsers(): Observable<Users> = usersAgent.loadUsers()
}


