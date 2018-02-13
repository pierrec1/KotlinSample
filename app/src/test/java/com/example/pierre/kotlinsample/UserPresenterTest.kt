package com.example.pierre.kotlinsample

import com.example.pierre.kotlinsample.users.UsersPresenter
import com.example.pierre.kotlinsample.users.model.GetUsersUseCase
import com.example.pierre.kotlinsample.users.model.User
import com.example.pierre.kotlinsample.users.model.Users

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


class UserPresenterTest {

    @Mock
    lateinit var mockGetUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun loadUser_showsUserList_whenUseCaseReturnsList() {
        Mockito.`when`(mockGetUsersUseCase.loadUsers()).thenReturn(Single.just(makeAnyUserList()))
        val usersPresenter = UsersPresenter(mockGetUsersUseCase)
        val view = mock(UsersPresenter.View::class.java)
        usersPresenter.setView(view)

        usersPresenter.loadUsers()

        verify(view).showUserList(ArgumentMatchers.any())
    }

    private fun makeAnyUserList(): Users {
        val users = ArrayList<User>()
        for (i in 0..4) {
            val user = User("email_" + i, "name_" + i, "infos_" + i)
            users.add(user)
        }
        return Users(users)
    }
}