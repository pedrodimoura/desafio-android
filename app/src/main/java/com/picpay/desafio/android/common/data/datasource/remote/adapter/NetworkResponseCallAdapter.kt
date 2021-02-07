package com.picpay.desafio.android.common.data.datasource.remote.adapter

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkError
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class NetworkResponseCallAdapter<T : Any>(
    private val successType: Type
) : CallAdapter<T, Call<NetworkResponse<T>>> {
    override fun responseType(): Type = successType

    override fun adapt(call: Call<T>): Call<NetworkResponse<T>> =
        NetworkResponseCall(call)
}
