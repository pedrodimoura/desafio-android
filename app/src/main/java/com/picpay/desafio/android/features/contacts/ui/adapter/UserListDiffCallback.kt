package com.picpay.desafio.android.features.contacts.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.features.contacts.domain.model.User

class UserListDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
}
