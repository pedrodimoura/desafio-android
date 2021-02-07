package com.picpay.desafio.android.features.contacts.ui.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.domain.Error
import com.picpay.desafio.android.common.domain.Result
import com.picpay.desafio.android.features.contacts.domain.model.User
import com.picpay.desafio.android.features.contacts.domain.repository.UserRepository
import com.picpay.desafio.android.features.contacts.ui.model.MainViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var observer: Observer<MainViewState>

    private val viewModel: MainViewModel by lazy {
        MainViewModel(userRepository, testCoroutineDispatcher)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @Test
    fun shouldFetchUsersSucceed() = runBlockingTest {
        val expected = emptyList<User>()
        whenever(userRepository.getUsers()).thenReturn(Result.Success(expected))

        viewModel.fetchUsersLiveData.observeForever(observer)
        viewModel.fetchUsers()

        verify(userRepository, times(1)).getUsers()

        verify(observer, times(1)).onChanged(MainViewState.Loading)
        verify(observer, times(1)).onChanged(MainViewState.ShowUserOnUI(expected))
        verify(observer, times(1)).onChanged(MainViewState.Done)

        viewModel.fetchUsersLiveData.removeObserver(observer)
    }

    @Test
    fun shouldFetchUserFail() = runBlockingTest {
        val expected = Error(-10, "Error Title", "Error Message")
        whenever(userRepository.getUsers()).thenReturn(Result.Failure(expected))

        viewModel.fetchUsersLiveData.observeForever(observer)

        viewModel.fetchUsers()

        verify(userRepository, times(1)).getUsers()

        verify(observer, times(1)).onChanged(MainViewState.Loading)
        verify(observer, times(1)).onChanged(
            MainViewState.ShowFailure(expected.title, expected.message)
        )
        verify(observer, times(1)).onChanged(MainViewState.Done)

        viewModel.fetchUsersLiveData.removeObserver(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}
