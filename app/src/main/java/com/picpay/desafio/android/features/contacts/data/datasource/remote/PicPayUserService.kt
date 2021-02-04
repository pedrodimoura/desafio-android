package com.picpay.desafio.android.features.contacts.data.datasource.remote

import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.User
import retrofit2.Call
import retrofit2.http.GET


interface PicPayUserService {
    @GET("users")
    fun getUsers(): Call<List<User>>
}
