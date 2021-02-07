package com.picpay.desafio.android.features.contacts.ui.model

import com.picpay.desafio.android.features.contacts.domain.model.User

sealed class MainViewState {
    object Loading : MainViewState()
    data class ShowUserOnUI(val users: List<User>) : MainViewState()
    data class ShowFailure(val title: String, val message: String) : MainViewState()
    object Done : MainViewState()
}
