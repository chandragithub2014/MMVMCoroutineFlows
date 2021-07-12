package com.employeeinfo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.employeeinfo.R
import com.employeeinfo.model.UserModel
import kotlinx.android.synthetic.main.item_user_list.view.*

class UserListAdapter(var users: ArrayList<UserModel.Data>, var context: Context) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder  =
        UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user_list,parent,false))

    override fun getItemCount(): Int  = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int)  = holder.bind(users[position])

    fun refreshAdapter( newUsers:List<UserModel.Data>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner  class UserViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private  val layout = view.item_layout
        private  val firstName = view.first_name
        private val lastName = view.last_name
        private val email = view.email
        private val image = view.imageView
        fun bind(userModel: UserModel.Data){
            firstName.text = userModel.first_name
            lastName.text = userModel.last_name
            email.text = userModel.email
            Glide.with(context).load(userModel.avatar).placeholder(R.drawable.ic_sync).into(image)



        }
    }
}