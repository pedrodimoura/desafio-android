package com.picpay.desafio.android

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.data.datasource.remote.service.PicPayUserService

class ExampleService(
    private val userService: PicPayUserService
) {

    suspend fun example(): NetworkResponse<List<UserResponsePayload>> {
        return userService.getUsers()
    }
}