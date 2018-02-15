package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersAgent @Inject constructor(private val usersNetworkDataSource: UsersNetworkDataSource,
                                     private val usersLocalDataSource: UsersLocalDataSource) {

    fun loadUsers(): Single<Users> {
        if (!usersLocalDataSource.hasUsers()) {
            return usersNetworkDataSource.getUserList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSuccess({ users: Users -> saveUserList(users) })
        }
        else {
            return Single.just(Users(usersLocalDataSource.getUsers()))
        }
    }

    fun saveUserList(users: Users?): Single<Users> =
            usersLocalDataSource.saveUsers(users)

    fun searchUsers(textToMatch: String): Single<List<User>> =
            usersLocalDataSource.searchUsers(textToMatch)
}


