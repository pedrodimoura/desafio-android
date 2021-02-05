package com.picpay.desafio.android.features.contacts.di

import com.picpay.desafio.android.common.di.DEFAULT_RETROFIT
import com.picpay.desafio.android.features.contacts.data.datasource.remote.UserRemoteDataSource
import com.picpay.desafio.android.features.contacts.data.datasource.remote.impl.UserRemoteDataSourceImpl
import com.picpay.desafio.android.features.contacts.data.datasource.remote.service.PicPayUserService
import com.picpay.desafio.android.features.contacts.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.features.contacts.domain.repository.UserRepository
import com.picpay.desafio.android.features.contacts.ui.adapter.UserListAdapter
import com.picpay.desafio.android.features.contacts.ui.adapter.UserListDiffCallback
import com.picpay.desafio.android.features.contacts.ui.vm.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainFeatureModule = module {
    // Data Layer
    single<PicPayUserService> {
        get<Retrofit>(named(DEFAULT_RETROFIT)).create(PicPayUserService::class.java)
    }
    single<UserRemoteDataSource> { UserRemoteDataSourceImpl(picPayUserService = get()) }
    single<UserRepository> { UserRepositoryImpl(userRemoteDataSource = get()) }

    // UI Layer
    viewModel { MainViewModel(userRepository = get()) }
    single { UserListAdapter(UserListDiffCallback()) }
}