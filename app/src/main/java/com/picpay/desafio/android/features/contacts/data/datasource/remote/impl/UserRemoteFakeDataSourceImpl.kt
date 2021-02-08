package com.picpay.desafio.android.features.contacts.data.datasource.remote.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload

class UserRemoteFakeDataSourceImpl(private val gson: Gson) : UserRemoteDataSource {

    override suspend fun getUsers(): NetworkResponse<List<UserResponsePayload>> {
        val body =
            "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":null,\"username\":\"@eduardo.santos\"}]"
        val users = gson.fromJson<List<UserResponsePayload>>(
            body,
            object : TypeToken<List<UserResponsePayload>>() {}.type
        )
        return NetworkResponse.Success(users)
    }
}