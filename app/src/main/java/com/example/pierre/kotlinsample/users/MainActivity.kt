package com.example.pierre.kotlinsample.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.widget.LinearLayout
import android.widget.Toast
import com.example.pierre.kotlinsample.R
import com.example.pierre.kotlinsample.users.dagger.MyApplication
import com.example.pierre.kotlinsample.users.model.Users
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), UsersPresenter.View, UserListView {

    @Inject
    lateinit var presenter: UsersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MyApplication).appComponent.inject(this)
        setSupportActionBar(tool_bar)
        setUpSearchView()
        presenter.setView(this)
        presenter.loadUsers()
    }

    private fun setUpSearchView() {
        search_view.setIconifiedByDefault(false)
        search_view.setFocusable(false)
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(textToMatch: String): Boolean {
                presenter.searchUsers(textToMatch)
                return false
            }

            override fun onQueryTextChange(textToMatch: String): Boolean {
                if(textToMatch == "") {
                    presenter.searchUsers("")
                }
                return true
            }
        })
    }

    override fun showLoading() {
        Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
    }

    override fun hideLoading() {
        Toast.makeText(this, "done", Toast.LENGTH_LONG).show()
    }

    override fun showUserList(users: Users?) {
        if (users != null) {
            Toast.makeText(this, "Nb users: " + users.userList.size, Toast.LENGTH_LONG).show()
            user_list_recycler_view?.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            user_list_recycler_view?.adapter = UserListAdapter(this, users.userList)
        }
    }

    override fun showError(error: String?) {
        Toast.makeText(this, getString(R.string.error) + error, Toast.LENGTH_LONG).show()
    }

    override fun onItemClicked(name: String?) {
        Toast.makeText(this, "clicked on " + name, Toast.LENGTH_LONG).show()
        search_view.setQuery(name, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}

