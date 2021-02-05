package com.picpay.desafio.android.features.contacts.data.datasource.remote.service

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import retrofit2.http.GET

interface PicPayUserService {
    @GET("users")
    suspend fun getUsers(): NetworkResponse<List<UserResponsePayload>>
}
