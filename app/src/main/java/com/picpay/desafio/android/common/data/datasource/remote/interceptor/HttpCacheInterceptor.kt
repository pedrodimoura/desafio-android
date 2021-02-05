package com.picpay.desafio.android.common.data.datasource.remote.interceptor

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response


class HttpCacheInterceptor(
    private val cacheConfiguration: CacheConfiguration
) : Interceptor {

    companion object {
        @JvmStatic
        val CONDITIONAL = arrayListOf(
            "no-store",
            "no-cache",
            "must-revalidate",
            "max-stale=0"
        )
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val cacheControl = originalResponse.header("Cache-Control")

        return when {
            cacheControl == null || cacheControl.findAnyOf(CONDITIONAL) != null -> {
                val cc = CacheControl.Builder()
                    .maxAge(cacheConfiguration.maxAge, cacheConfiguration.maxAgeTimeUnit)
                    .build()
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", cc.toString())
                    .build()
            }
            else -> originalResponse
        }
    }
}
