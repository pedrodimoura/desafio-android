package com.picpay.desafio.android.features.contacts.ui.model

sealed class MainViewState {
    object Loading : MainViewState()
    data class ShowUserOnUI(val users: List<UserView>) : MainViewState()
    data class ShowFailure(val title: String, val message: String) : MainViewState()
    object Done : MainViewState()
}
