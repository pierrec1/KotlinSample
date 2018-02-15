package com.example.pierre.kotlinsample

import com.example.pierre.kotlinsample.users.UsersPresenter
import com.example.pierre.kotlinsample.users.model.GetUsersUseCase
import com.example.pierre.kotlinsample.users.model.SearchUsersUseCase
import com.example.pierre.kotlinsample.users.model.User
import com.example.pierre.kotlinsample.users.model.Users
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.given

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*


class UsersPresenterTest {

    var view = mock(UsersPresenter.View::class.java)

    @Mock
    lateinit var mockGetUsersUseCase: GetUsersUseCase

    @Mock
    lateinit var mockSearchUsersUseCase: SearchUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
    }

    @Test
    fun loadUsers_showsUsers_whenUseCaseReturnsList() {
        Mockito.`when`(mockGetUsersUseCase.loadUsers()).thenReturn(Single.just(makeAnyUsers()).toObservable())
        val presenter = UsersPresenter(mockGetUsersUseCase, mockSearchUsersUseCase)
        presenter.setView(view)

        presenter.loadUsers()

        verify(view).showUserList(any())
    }

    @Test
    fun loadUsers_showsError_whenUseCaseThrows() {
        val throwable = Throwable("test")
        Mockito.`when`(mockGetUsersUseCase.loadUsers()).thenReturn(Single.error<Users>(throwable).toObservable())
        val presenter = UsersPresenter(mockGetUsersUseCase, mockSearchUsersUseCase)
        presenter.setView(view)

        presenter.loadUsers()

        verify(view).showError(any())
    }

    @Test
    fun searchUsers_showsUserList_whenUserMatchesName() {
        Mockito.`when`(mockSearchUsersUseCase.searchUsers(any())).thenReturn(Single.just(makeAnyUserList()))
        val presenter = UsersPresenter(mockGetUsersUseCase, mockSearchUsersUseCase)
        presenter.setView(view)

        presenter.searchUsers("name")

        verify(view).showUserList(any())
    }

    @Test
    fun searchUsers_showsError_whenUserCaseThrows() {
        val throwable = Throwable("test")
        given(mockSearchUsersUseCase.searchUsers(any())).willReturn(Single.error<List<User>>(throwable))
        val presenter = UsersPresenter(mockGetUsersUseCase, mockSearchUsersUseCase)
        presenter.setView(view)

        presenter.searchUsers("name")

        verify(view).showError(any())
    }

    private fun makeAnyUsers(): Users {
        return Users(makeAnyUserList())
    }

    private fun makeAnyUserList(): List<User> {
        val usersList = ArrayList<User>()
        for (i in 0..4) {
            val user = User("email_" + i, "name_" + i, "infos_" + i)
            usersList.add(user)
        }
        return usersList
    }
}