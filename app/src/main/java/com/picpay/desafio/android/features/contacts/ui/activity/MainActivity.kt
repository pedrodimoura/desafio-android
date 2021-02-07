package com.picpay.desafio.android.features.contacts.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.common.ui.gone
import com.picpay.desafio.android.common.ui.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.features.contacts.ui.MainView
import com.picpay.desafio.android.features.contacts.ui.adapter.UserListAdapter
import com.picpay.desafio.android.features.contacts.ui.model.MainViewState
import com.picpay.desafio.android.features.contacts.ui.vm.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), MainView {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()
    private val userListAdapter: UserListAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    override fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.adapter = userListAdapter
    }

    override fun onResume() {
        super.onResume()
        setupUserObserver()
        fetchUsers()
    }

    override fun showProgress() {
        binding.userListProgressBar.visible()
    }

    override fun hideProgress() {
        binding.userListProgressBar.gone()
    }

    override fun showContent() {
        binding.recyclerView.visible()
    }

    override fun hideContent() {
        binding.recyclerView.gone()
    }

    override fun setupUserObserver() {
        viewModel.fetchUsersLiveData.observe(this) { mainViewState ->
            when (mainViewState) {
                is MainViewState.Loading -> showProgress()
                is MainViewState.ShowUserOnUI -> showUserOnUi(mainViewState)
                is MainViewState.ShowFailure -> showFailure(mainViewState)
                is MainViewState.Done -> hideProgress()
            }
        }
    }

    override fun fetchUsers() = viewModel.fetchUsers()

    private fun showUserOnUi(showUserOnUI: MainViewState.ShowUserOnUI) {
        showContent()
        userListAdapter.submitList(showUserOnUI.users)
    }

    private fun showFailure(showFailure: MainViewState.ShowFailure) {
        val message = getString(R.string.error)
        hideProgress()
        hideContent()
        Toast
            .makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }
}
