package com.picpay.desafio.android.features.contacts.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.features.contacts.ui.model.UserView

class UserListAdapter(diffCallback: DiffUtil.ItemCallback<UserView>) :
    ListAdapter<UserView, UserListItemViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListItemViewHolder {
        val binding = ListItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListItemViewHolder, position: Int) =
        holder.bind(getItem(position))
}

