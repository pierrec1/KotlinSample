package com.example.pierre.kotlinsample.users

import com.example.pierre.kotlinsample.users.model.GetUsersUseCase
import com.example.pierre.kotlinsample.users.model.Users
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UsersPresenter @Inject constructor(val getUserUseCase: GetUsersUseCase) {

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
                .subscribe({ userList -> view.showUserList(userList) }
                        , { throwable -> view.showError(throwable.message) })
        )
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    interface View {
        fun showLoading()

        fun hideLoading()

        fun showUserList(users: Users?)

        fun showError(error: String?)
    }
}

