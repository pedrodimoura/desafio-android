package com.picpay.desafio.android.common.di

import com.picpay.desafio.android.common.analytics.Analytics
import com.picpay.desafio.android.common.analytics.Tracker
import com.picpay.desafio.android.common.analytics.impl.AnalyticsImpl
import com.picpay.desafio.android.common.analytics.impl.AndroidLogTracker
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val ANDROID_LOG_TRACKER_NAME = "android-logger_tracker"

val analyticsModule = module {
    single<Tracker>(named(ANDROID_LOG_TRACKER_NAME)) { AndroidLogTracker() }
    single<Analytics> { AnalyticsImpl(mutableListOf(get(named(ANDROID_LOG_TRACKER_NAME)))) }
}
