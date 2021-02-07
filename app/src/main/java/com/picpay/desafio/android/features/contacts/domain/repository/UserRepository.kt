package com.picpay.desafio.android.features.contacts.domain.repository

import com.picpay.desafio.android.common.domain.Result
import com.picpay.desafio.android.features.contacts.domain.model.User

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
}
