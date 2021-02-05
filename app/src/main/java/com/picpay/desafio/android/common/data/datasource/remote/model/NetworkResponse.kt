package com.picpay.desafio.android.common.data.datasource.remote.model

sealed class NetworkResponse<out T : Any> {
    data class Success<T : Any>(val data: T) : NetworkResponse<T>()
    data class Failure(val error: NetworkError) : NetworkResponse<Nothing>()
}
