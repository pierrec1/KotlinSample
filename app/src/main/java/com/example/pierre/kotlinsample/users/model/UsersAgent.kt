package com.example.pierre.kotlinsample.users.model

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersAgent @Inject constructor(private val usersNetworkDataSource: UsersNetworkDataSource,
                                     private val usersLocalDataSource: UsersLocalDataSource) {

    fun loadUsers(): Observable<Users> {
        val networkSource: Single<List<User>> =
                usersNetworkDataSource.getUserList()
                        .map(Users::userList)
                        .subscribeOn(Schedulers.io())

        return usersLocalDataSource.getUsers()
                .flatMapObservable { listFromLocal: List<User> ->
                    networkSource
                            .observeOn(Schedulers.io())
                            .toObservable()
                            .filter { userList: List<User> ->
                                userList != listFromLocal
                            }
                            .flatMapSingle { userList ->
                                usersLocalDataSource.saveUsers(userList)
                            }
                            .startWith(Users(listFromLocal))
                }
    }

    fun searchUsers(textToMatch: String): Single<List<User>> =
            usersLocalDataSource.searchUsers(textToMatch)
}


