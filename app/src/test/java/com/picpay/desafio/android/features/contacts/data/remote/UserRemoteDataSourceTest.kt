package com.picpay.desafio.android.features.contacts.data.remote

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkError
import com.picpay.desafio.android.common.data.datasource.remote.model.NetworkResponse
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.impl.UserRemoteDataSourceImpl
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.data.datasource.remote.service.PicPayUserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest {

    @Mock
    private lateinit var picPayUserService: PicPayUserService

    private val userRemoteDataSource: UserRemoteDataSource by lazy {
        UserRemoteDataSourceImpl(picPayUserService)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun shouldGetUsersSucceed() = runBlockingTest {
        val expected = NetworkResponse.Success(emptyList<UserResponsePayload>())
        whenever(picPayUserService.getUsers()).thenReturn(expected)

        val result = userRemoteDataSource.getUsers()

        verify(picPayUserService, times(1)).getUsers()

        assertEquals(expected, result)
    }

    @Test
    fun shouldGetUsersFail() = runBlockingTest {
        val expected = NetworkResponse.Failure(NetworkError(-10, "Error Title", "Error Message"))
        whenever(picPayUserService.getUsers()).thenReturn(expected)

        val result = userRemoteDataSource.getUsers()

        verify(picPayUserService, times(1)).getUsers()

        assertEquals(expected, result)
    }
}
