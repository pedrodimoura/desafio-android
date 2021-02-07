package com.picpay.desafio.android.common.di

import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.common.data.datasource.remote.adapter.NetworkResponseCallFactory
import com.picpay.desafio.android.common.data.datasource.remote.interceptor.CacheConfiguration
import com.picpay.desafio.android.common.data.datasource.remote.interceptor.HttpCacheInterceptor
import com.picpay.desafio.android.common.data.datasource.remote.interceptor.HttpOfflineCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

const val DEFAULT_CLIENT = "default-client"
const val DEFAULT_RETROFIT = "default-retrofit"
private const val DEFAULT_CACHE = "default-cache"
private const val MAX_CACHE_SIZE: Long = 10 * 1024 * 1024
private const val DEFAULT_CACHE_DIR_NAME = "offline-cache"

val networkModule = module(createdAtStart = true) {
    factory { (cacheConfiguration: CacheConfiguration) ->
        HttpOfflineCacheInterceptor(cacheConfiguration)
    }
    factory { (cacheConfiguration: CacheConfiguration) ->
        HttpCacheInterceptor(cacheConfiguration)
    }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }
    single(named(DEFAULT_CACHE)) {
        Cache(File(androidApplication().cacheDir, DEFAULT_CACHE_DIR_NAME), MAX_CACHE_SIZE)
    }

    single(named(DEFAULT_CLIENT)) {
        val cacheConfiguration = CacheConfiguration(10, TimeUnit.MINUTES, 10, TimeUnit.MINUTES)
        OkHttpClient.Builder()
            .cache(get(named(DEFAULT_CACHE)))
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addNetworkInterceptor(get<HttpCacheInterceptor> { parametersOf(cacheConfiguration) })
            .addInterceptor(get<HttpOfflineCacheInterceptor> { parametersOf(cacheConfiguration) })
            .build()
    }

    single(named(DEFAULT_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named(DEFAULT_CLIENT)))
            .addCallAdapterFactory(NetworkResponseCallFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
