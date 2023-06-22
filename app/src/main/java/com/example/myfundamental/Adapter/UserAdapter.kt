package com.example.myfundamental.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myfundamental.R
import com.example.myfundamental.UserResult

class UserAdapter(private val listUser: List<UserResult>, private val onClick: (UserResult) ->  Unit) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tv_username.text = listUser[position].login
        viewHolder.tv_url.text = listUser[position].htmlUrl
        Glide.with(viewHolder.itemView.context)
            .load(listUser[position].avatarUrl)
            .apply(RequestOptions().override(55, 55))
            .into(viewHolder.img_profil)
        viewHolder.itemView.setOnClickListener {
            onClick(listUser[viewHolder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_username: TextView = view.findViewById(R.id.tv_username)
        val tv_url: TextView  = view.findViewById(R.id.tv_url)
        val img_profil: ImageView = itemView.findViewById(R.id.image_profil)
    }
}