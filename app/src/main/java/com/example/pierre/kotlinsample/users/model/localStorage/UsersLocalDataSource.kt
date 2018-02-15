package com.example.pierre.kotlinsample.users.model.localStorage

import com.example.pierre.kotlinsample.users.model.User
import com.example.pierre.kotlinsample.users.model.Users
import io.reactivex.Single
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun saveUsers(userList: List<User>): Single<Users> {
        userDao.deleteAll()
        for (user in userList) {
            userDao.insert(user)
        }
        return Single.just(Users(userDao.getAll()))
    }

    fun getUsers(): Single<List<User>> = Single.just(userDao.getAll())

    fun searchUsers(textToMatch: String): Single<List<User>> =
        Single.just(userDao.findByName(textToMatch ))

}