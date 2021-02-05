package com.picpay.desafio.android.features.contacts.data.datasource.remote.impl

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.data.datasource.remote.service.PicPayUserService

class UserRemoteDataSourceImpl(
    private val picPayUserService: PicPayUserService
) : UserRemoteDataSource {
    override suspend fun getUsers(): NetworkResponse<List<UserResponsePayload>> =
        picPayUserService.getUsers()
}
