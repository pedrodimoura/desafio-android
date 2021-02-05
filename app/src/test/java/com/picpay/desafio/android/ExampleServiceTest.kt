package com.picpay.desafio.android

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.features.contacts.data.datasource.remote.model.UserResponsePayload
import com.picpay.desafio.android.features.contacts.data.datasource.remote.service.PicPayUserService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class ExampleServiceTest {

    private val api = mock<PicPayUserService>()

    private val service = ExampleService(api)

    @Test
    fun exampleTest() = runBlockingTest {
        // given
        val expectedUsers = emptyList<UserResponsePayload>()

        whenever(api.getUsers()).thenReturn(mock())

        // when
        val users = service.example()

        // then
        assertEquals(users, expectedUsers)
    }
}