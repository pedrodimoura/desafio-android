package com.picpay.desafio.android.common.analytics

interface Tracker {

    fun isAnalyticsEnabled(): Boolean

    fun enableAnalytics()

    fun disableAnalytics()

    fun logLevel(logLevel: Int)

    fun start()

    fun apiKey(): String

    fun trackEvent(event: Event)

}
