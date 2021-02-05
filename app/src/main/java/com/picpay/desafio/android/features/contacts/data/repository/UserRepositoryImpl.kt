package com.picpay.desafio.android.features.contacts.data.repository

import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkError
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.Result
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.domain.model.User
import com.picpay.desafio.android.features.contacts.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUsers(): Result<List<User>> {
        return when (val networkResponse = userRemoteDataSource.getUsers()) {
            is NetworkResponse.Success -> handleSuccess(networkResponse.data)
            is NetworkResponse.Failure -> emitFailure(networkResponse.error)
        }
    }

    private fun handleSuccess(networkResponse: List<UserResponsePayload>) =
        Result.Success(networkResponse.map { userResponsePayload ->
            User(
                userResponsePayload.img,
                userResponsePayload.name,
                userResponsePayload.id,
                userResponsePayload.username
            )
        })

    private fun emitFailure(networkError: NetworkError) =
        Result.Failure(Error(networkError.code, networkError.title, networkError.message))
}