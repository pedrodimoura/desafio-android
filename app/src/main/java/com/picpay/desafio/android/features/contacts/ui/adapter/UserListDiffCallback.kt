package com.picpay.desafio.android.features.contacts.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.desafio.android.features.contacts.ui.model.UserView

class UserListDiffCallback : DiffUtil.ItemCallback<UserView>() {
    override fun areItemsTheSame(oldItem: UserView, newItem: UserView): Boolean =
        oldItem.username == newItem.username

    override fun areContentsTheSame(oldItem: UserView, newItem: UserView): Boolean =
        oldItem == newItem
}
