package com.example.pierre.kotlinsample.users

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.pierre.kotlinsample.R
import com.example.pierre.kotlinsample.users.dagger.MyApplication
import com.example.pierre.kotlinsample.users.model.Users
import javax.inject.Inject

class MainActivity : AppCompatActivity(), UsersPresenter.View {

    @Inject
    lateinit var presenter: UsersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).appComponent.inject(this)

        presenter.setView(this)
        presenter.loadUsers()
    }

    override fun showLoading() {
        Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "done", Toast.LENGTH_LONG).show()
    }

    override fun showUserList(users: Users?) {
        if (users != null) {
            Toast.makeText(this, "users + " + users.userList.size, Toast.LENGTH_LONG).show()
        }
    }

    override fun showError(error: String?) {
        Toast.makeText(this, getString(R.string.error) + error, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}

