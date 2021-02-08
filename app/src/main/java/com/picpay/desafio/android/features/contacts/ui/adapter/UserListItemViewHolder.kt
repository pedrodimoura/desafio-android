package com.picpay.desafio.android.features.contacts.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.ui.gone
import com.picpay.desafio.android.common.ui.visible
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.features.contacts.ui.model.UserView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val listItemUserBinding: ListItemUserBinding
) : RecyclerView.ViewHolder(listItemUserBinding.root) {

    fun bind(user: UserView) {
        when {
            user.name.isNotEmpty() -> listItemUserBinding.name.text = user.name
            else -> listItemUserBinding.name.setText(R.string.app_name)
        }

        when {
            user.username.isNotEmpty() -> listItemUserBinding.username.text = user.username
            else -> listItemUserBinding.name.setText(R.string.app_name)
        }

        listItemUserBinding.progressBar.visible()

        when {
            user.img.isNotEmpty() -> {
                Picasso.get()
                    .load(user.img)
                    .error(R.drawable.ic_round_account_circle)
                    .placeholder(R.drawable.ic_round_account_circle)
                    .into(listItemUserBinding.picture, object : Callback {
                        override fun onSuccess() = listItemUserBinding.progressBar.gone()
                        override fun onError(e: Exception?) = listItemUserBinding.progressBar.gone()
                    })
            }
            else -> {
                listItemUserBinding.picture.setImageResource(R.drawable.ic_round_account_circle)
                listItemUserBinding.progressBar.gone()
            }
        }


    }
}