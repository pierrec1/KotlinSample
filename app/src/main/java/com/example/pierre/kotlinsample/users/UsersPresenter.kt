package com.example.pierre.kotlinsample.users

import com.example.pierre.kotlinsample.users.model.GetUsersUseCase
import com.example.pierre.kotlinsample.users.model.SearchUsersUseCase
import com.example.pierre.kotlinsample.users.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersPresenter @Inject constructor(
        val getUserUseCase: GetUsersUseCase,
        val searchUsersUseCase: SearchUsersUseCase) {

    private lateinit var view: View
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun setView(view: View) {
        this.view = view
    }

    fun loadUsers() {
        view.showLoading()
        compositeDisposable.add(getUserUseCase.loadUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ users -> showUserList(users.userList) }
                        , { throwable -> view.showError(throwable.message) }))
    }

    private fun showUserList(userList: List<User>?) {
        if (userList != null) {
            view.showUserList(userList)
        }
    }

    fun searchUsers(textToMatch: String) {
        compositeDisposable.add(searchUsersUseCase.searchUsers(textToMatch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList -> showUserList(userList) }
                        , { throwable -> view.showError(throwable.message) })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showUserList(userList: List<User>)

        fun showError(error: String?)
    }
}

