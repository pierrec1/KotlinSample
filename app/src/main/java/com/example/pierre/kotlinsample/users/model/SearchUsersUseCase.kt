package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import javax.inject.Inject

open class SearchUsersUseCase @Inject constructor(private val usersAgent: UsersAgent) {

    open fun searchUsers(textToMatch: String): Single<List<User>> = usersAgent.searchUsers(textToMatch)
}


