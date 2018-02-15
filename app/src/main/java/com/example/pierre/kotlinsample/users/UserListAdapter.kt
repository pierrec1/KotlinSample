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

class UserListAdapter(val view: UserListView, val userList: List<User>): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private var selectedHolder: ViewHolder? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.name?.text = userList[position].name
        holder?.email?.text = userList[position].email
        holder?.infos?.text = userList[position].infos
        holder?.button?.setOnClickListener {
            view.onItemClicked(holder.name?.text?.toString())
            changeCellItemsAppearance(holder)
        }
    }

    private fun changeCellItemsAppearance(holder: ViewHolder) {
        if (selectedHolder?.infos?.visibility == View.VISIBLE) {
            selectedHolder?.infos?.visibility = View.GONE
            selectedHolder?.name?.setTypeface(null, Typeface.NORMAL)
            selectedHolder?.name?.setTextColor(Color.BLACK)
        }
        holder.infos?.visibility = View.VISIBLE
        holder.name?.setTypeface(null, Typeface.BOLD)
        holder.name?.setTextColor(Color.BLUE)
        selectedHolder = holder
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.name
        val email = itemView.email
        val infos = itemView.infos
        val button = itemView.set_button
    }
}

interface UserListView {
    fun onItemClicked(name: String?)
}
