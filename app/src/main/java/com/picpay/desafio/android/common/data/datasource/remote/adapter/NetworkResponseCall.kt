package com.picpay.desafio.android.common.data.datasource.remote.adapter

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkError
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

internal class NetworkResponseCall<T : Any>(
    private val delegate: Call<T>
) : Call<NetworkResponse<T>> {

    companion object {
        @JvmStatic
        val UNKNOWN_ERROR = NetworkError(-1, "Unknown Error", "Try again")
    }

    override fun enqueue(callback: Callback<NetworkResponse<T>>) {
        return delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val responseCode = response.code()
                val responseBody = response.body()
                val responseErrorBody = response.errorBody()

                when {
                    response.isSuccessful -> {
                        responseBody?.let { body ->
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Success(body))
                            )
                        } ?: Response.success(NetworkResponse.Failure(UNKNOWN_ERROR))
                    }
                    else -> {
                        if (responseErrorBody == null ||
                            responseErrorBody.contentLength() == 0L
                        ) {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(NetworkResponse.Failure(UNKNOWN_ERROR))
                            )
                        } else {
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(
                                    NetworkResponse.Failure(mapToServiceError(responseCode))
                                )
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkError = when (t) {
                    is HttpException -> NetworkError(t.code(), t.message(), t.message())
                    else -> UNKNOWN_ERROR
                }
                val networkResponse = NetworkResponse.Failure(networkError)
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }
        })
    }

    private fun mapToServiceError(responseCode: Int): NetworkError {
        return when (responseCode) {
            ServiceError.Code.USER_NOT_FOUND_SERVICE_ERROR.code ->
                NetworkError(
                    ServiceError.Code.USER_NOT_FOUND_SERVICE_ERROR.code,
                    ServiceError.Code.USER_NOT_FOUND_SERVICE_ERROR.title,
                    ServiceError.Code.USER_NOT_FOUND_SERVICE_ERROR.message
                )
            else -> UNKNOWN_ERROR
        }
    }

    override fun clone(): Call<NetworkResponse<T>> = NetworkResponseCall(delegate.clone())

    override fun execute(): Response<NetworkResponse<T>> =
        throw UnsupportedOperationException("This operation is not supported on NetworkResponseCall")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
