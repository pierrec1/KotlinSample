package com.example.pierre.kotlinsample.users

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.pierre.kotlinsample.users.model.User
import com.example.pierre.kotlinsample.R

class UserListAdapter(val view: UserListView, val userList: List<User>): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    var selectedHolder: ViewHolder? = null

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.name?.text = userList[position].name
        holder?.email?.text = userList[position].email
        holder?.infos?.text = userList[position].infos
        holder?.button?.setOnClickListener {
            view.onItemClicked(holder.name?.text?.toString())
            changeInfosVisibility(holder)
        }
    }

    private fun changeInfosVisibility(holder: ViewHolder) {
        if (selectedHolder?.infos?.visibility == View.VISIBLE) {
            selectedHolder?.infos?.visibility = View.GONE
        }
        holder.infos?.visibility = View.VISIBLE
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
        val name = itemView.findViewById<TextView>(R.id.name)
        val email = itemView.findViewById<TextView>(R.id.email)
        val infos = itemView.findViewById<TextView>(R.id.infos)
        val button = itemView.findViewById<Button>(R.id.set_button)
    }
}

interface UserListView {
    fun onItemClicked(name: String?)
}
