package com.picpay.desafio.android.common.data.datasource.remote.adapter

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseCallFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType))
            return null

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Any>> or Call<NetworkResponse<out Any>>"
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as NetworkResponse<Any> or NetworkResponse<out Any>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResponseCallAdapter<Any>(successBodyType)
    }
}