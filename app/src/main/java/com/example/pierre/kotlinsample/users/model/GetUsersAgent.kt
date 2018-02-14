package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUsersAgent @Inject constructor(private val getUsersNetworkDataSource: GetUsersNetworkDataSource,
                                        private val getUsersLocalDataSource: GetUsersLocalDataSource) {

    fun loadUsers(): Single<Users> = getUsersNetworkDataSource.getUserList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess({
                users: Users -> saveUserList(users)
            })

    fun saveUserList(users: Users?): Single<Users> =
        getUsersLocalDataSource.saveUsers(users)

    fun searchUsers(textToMatch: String): Single<List<User>> =
        getUsersLocalDataSource.searchUsers(textToMatch)
}


