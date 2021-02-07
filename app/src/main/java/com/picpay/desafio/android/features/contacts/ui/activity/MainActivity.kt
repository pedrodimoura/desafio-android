package com.picpay.desafio.android.features.contacts.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.common.analytics.Analytics
import com.picpay.desafio.android.common.analytics.EventName
import com.picpay.desafio.android.common.ui.FeatureModuleInitializer
import com.picpay.desafio.android.common.ui.gone
import com.picpay.desafio.android.common.ui.visible
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.features.contacts.di.mainFeatureModule
import com.picpay.desafio.android.features.contacts.ui.MainView
import com.picpay.desafio.android.features.contacts.ui.adapter.UserListAdapter
import com.picpay.desafio.android.features.contacts.ui.model.MainViewState
import com.picpay.desafio.android.features.contacts.ui.vm.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module

@KoinApiExtension
class MainActivity : FeatureModuleInitializer(), MainView {

    companion object {
        private const val PAGE_NAME = "main-activity"
    }

    override val module: Module = mainFeatureModule

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModel()
    private val userListAdapter: UserListAdapter by inject()
    private val analytics: Analytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
        setupUserObserver()
        analytics.track {
            this.name = EventName.PAGE_VIEW
            this.put(EventName.PAGE_NAME, PAGE_NAME)
        }
        lifecycleScope.launchWhenResumed { fetchUsers() }
    }

    override fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerView.adapter = userListAdapter
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
        val message = showFailure.message
        hideProgress()
        hideContent()
        Toast
            .makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }
}
