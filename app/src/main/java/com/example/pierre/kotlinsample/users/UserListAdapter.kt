package com.example.pierre.kotlinsample.users

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pierre.kotlinsample.users.model.User
import com.example.pierre.kotlinsample.R
import kotlinx.android.synthetic.main.user_item.view.*

class UserListAdapter(val view: UserListView, val userList: List<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val NO_ITEM_SELECTED: Int = -1
    private var selectedItemPosition: Int = NO_ITEM_SELECTED

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.name?.text = userList[position].name
        holder?.email?.text = userList[position].email
        holder?.infos?.text = userList[position].infos
        setItemState(isItemSelected(position), holder)
        holder?.button?.setOnClickListener { onItemClicked(holder, position) }
    }

    private fun onItemClicked(holder: ViewHolder?, position: Int) {
        view.setActionBarTitle(holder?.name?.text?.toString())
        if (!isItemSelected(position)) {
            selectedItemPosition = position
            notifyDataSetChanged()
        }
    }

    private fun isItemSelected(position: Int) = position == selectedItemPosition

    private fun setItemState(selected: Boolean, holder: ViewHolder?) {
        holder?.infos?.visibility = if (selected) View.VISIBLE else View.GONE
        holder?.name?.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
        holder?.name?.setTextColor(if (selected) Color.BLUE else Color.BLACK)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.name
        val email = itemView.email
        val infos = itemView.infos
        val button = itemView.set_button
    }
}

interface UserListView {
    fun setActionBarTitle(name: String?)
}
