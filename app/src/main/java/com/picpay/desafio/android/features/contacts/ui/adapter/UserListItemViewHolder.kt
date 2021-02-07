package com.picpay.desafio.android.features.contacts.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.ui.gone
import com.picpay.desafio.android.common.ui.visible
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.features.contacts.domain.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val listItemUserBinding: ListItemUserBinding
) : RecyclerView.ViewHolder(listItemUserBinding.root) {

    fun bind(user: User) {
        listItemUserBinding.name.text = user.name
        listItemUserBinding.username.text = user.username
        listItemUserBinding.progressBar.visible()
        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(listItemUserBinding.picture, object : Callback {
                override fun onSuccess() {
                    listItemUserBinding.progressBar.gone()
                }

                override fun onError(e: Exception?) {
                    listItemUserBinding.progressBar.gone()
                }
            })
    }
}