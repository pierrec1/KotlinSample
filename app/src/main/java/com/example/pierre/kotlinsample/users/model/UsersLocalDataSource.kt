package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun saveUsers(users: Users?): Single<Users> {
        val userList = users?.userList

        if (userList != null) {
            for (user in userList) {
                userDao.insert(user)
            }
        }
        return Single.just(Users(userDao.getAll()))
    }

    fun searchUsers(textToMatch: String): Single<List<User>> {
        val nb: Int = userDao.getAll().size
        val ul: List<User> = userDao.findByName(textToMatch)
        val nb2: Int = ul.size
        return Single.just(userDao.findByName(textToMatch ))
    }

    fun clearUsers() {
        userDao.removeAll()
    }

    fun hasUsers(): Boolean = userDao.getAll().size > 0

    fun getUsers(): List<User> = userDao.getAll()
}