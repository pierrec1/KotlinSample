package com.example.pierre.kotlinsample.users.model

import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(userDao: UserDao) {

    var userList: List<User>? = ArrayList<User>()

    fun saveUsers(users: Users?): Single<Users> {
        userList = users?.userList
      return Single.just(users)
    }

    fun searchUsers(textToMatch: String): Single<List<User>> {
        val matchingUsers: ArrayList<User> = ArrayList<User>()
        for (user in userList.orEmpty()) {
            if(user.name.contains(textToMatch))
                matchingUsers.add(user)
        }
        return Single.just(matchingUsers)
    }
}