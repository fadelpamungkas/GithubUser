package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.model.User
import com.example.githubuser.databinding.CardviewUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.CardViewViewHolder>() {

    private var listUsers = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(dataUser: ArrayList<User>){
        listUsers.clear()
        listUsers.addAll(dataUser)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val binding = CardviewUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewViewHolder(binding)
    }
    override fun getItemCount(): Int = listUsers.size
    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }
    inner class CardViewViewHolder(private val binding: CardviewUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            with(binding){
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions().override(350, 350))
                    .into(imageMain)

                usernameMain.text = user.username

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}

