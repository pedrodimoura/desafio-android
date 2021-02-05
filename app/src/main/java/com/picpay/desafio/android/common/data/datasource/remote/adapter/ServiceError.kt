package com.picpay.desafio.android.common.data.datasource.remote.adapter

sealed class ServiceError {
    enum class Code(val code: Int, val title: String, val message: String) {
        USER_NOT_FOUND_SERVICE_ERROR(code = 608, title = "User Not Found", message = "User not found")
    }
}