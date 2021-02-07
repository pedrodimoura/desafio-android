package com.picpay.desafio.android.features.contacts.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkError
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.Result
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userRemoteDataSource)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldGetUsersSucceed() = runBlockingTest {
        val expected = emptyList<UserResponsePayload>()
        whenever(userRemoteDataSource.getUsers()).thenReturn(NetworkResponse.Success(expected))

        val result = userRepository.getUsers()

        verify(userRemoteDataSource, times(1)).getUsers()

        assertEquals(Result.Success(expected), result)
    }

    @Test
    fun shouldGetUsersFail() = runBlockingTest {
        val expected = NetworkError(-10, "Error Title", "Error Message")
        whenever(userRemoteDataSource.getUsers()).thenReturn(NetworkResponse.Failure(expected))

        val result = userRepository.getUsers()

        verify(userRemoteDataSource, times(1)).getUsers()

        assertEquals(
            Result.Failure(
                Error(
                    -10,
                    "Error Title",
                    "Error Message"
                )
            ), result
        )
    }
}
