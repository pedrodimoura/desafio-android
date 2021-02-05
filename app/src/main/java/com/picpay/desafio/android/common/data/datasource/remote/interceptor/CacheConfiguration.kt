package com.picpay.desafio.android.common.data.datasource.remote.interceptor

import java.util.concurrent.TimeUnit

data class CacheConfiguration(
    val maxStale: Int,
    val maxScaleTimeUnit: TimeUnit,
    val maxAge: Int,
    val maxAgeTimeUnit: TimeUnit
)
