package com.picpay.desafio.android.features.contacts.ui

interface MainView {
    fun initRecyclerView()
    fun setupUserObserver()
    fun fetchUsers()
    fun showProgress()
    fun hideProgress()
    fun hideContent()
    fun showContent()
}
