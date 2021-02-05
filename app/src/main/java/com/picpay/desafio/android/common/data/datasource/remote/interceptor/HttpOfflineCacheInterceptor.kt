package com.picpay.desafio.android.common.data.datasource.remote.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class HttpOfflineCacheInterceptor(
    private val cacheConfiguration: CacheConfiguration
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(cacheConfiguration.maxStale, cacheConfiguration.maxScaleTimeUnit)
                .maxAge(cacheConfiguration.maxAge, cacheConfiguration.maxAgeTimeUnit)
                .build()
            val offlineRequest = chain.request().newBuilder()
                .cacheControl(cacheControl)
                .build()
            chain.proceed(offlineRequest)
        }
    }
}
