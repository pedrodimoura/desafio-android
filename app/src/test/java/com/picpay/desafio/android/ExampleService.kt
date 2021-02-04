package com.picpay.desafio.android

import com.picpay.desafio.android.features.contacts.data.datasource.remote.PicPayUserService
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.User

class ExampleService(
    private val userService: PicPayUserService
) {

    fun example(): List<User> {
        val users = userService.getUsers().execute()

        return users.body() ?: emptyList()
    }
}