package com.picpay.desafio.android.features.contacts.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.common.domain.Result
import com.picpay.desafio.android.features.contacts.domain.repository.UserRepository
import com.picpay.desafio.android.features.contacts.ui.model.MainViewState
import com.picpay.desafio.android.features.contacts.ui.model.UserView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val userRepository: UserRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _fetchUsersFlow = MutableLiveData<MainViewState>()
    val fetchUsersLiveData: LiveData<MainViewState> = _fetchUsersFlow

    fun fetchUsers() {
        viewModelScope.launch(Dispatchers.Main) {
            _fetchUsersFlow.value = MainViewState.Loading
            val mainViewState =
                when (val result = withContext(coroutineDispatcher) { userRepository.getUsers() }) {
                    is Result.Success -> MainViewState.ShowUserOnUI(result.data.map { user ->
                        UserView(user.img, user.name, user.id, user.username)
                    })
                    is Result.Failure -> MainViewState.ShowFailure(
                        result.error.title,
                        result.error.message
                    )
                }
            _fetchUsersFlow.value = mainViewState
            _fetchUsersFlow.value = MainViewState.Done
        }
    }
}
