package com.picpay.desafio.android.features.contacts.data.datasource.remote

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload

interface UserRemoteDataSource {
    suspend fun getUsers(): NetworkResponse<List<UserResponsePayload>>
}
