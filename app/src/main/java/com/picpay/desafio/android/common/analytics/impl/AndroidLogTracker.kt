package com.picpay.desafio.android.common.analytics.impl

import android.util.Log
import com.picpay.desafio.android.common.analytics.Event
import com.picpay.desafio.android.common.analytics.Tracker
import java.util.*

class AndroidLogTracker : Tracker {

    companion object {
        const val TRACKER_LOG_NAME = "sample-tracker-log"
    }

    private var isEnabled: Boolean = false
    private val apiKey: UUID = UUID.randomUUID()

    override fun isAnalyticsEnabled() = isEnabled

    override fun enableAnalytics() {
        isEnabled = true
    }

    override fun disableAnalytics() {
        isEnabled = false
    }

    override fun logLevel(logLevel: Int) {
        throw UnsupportedOperationException()
    }

    override fun start() {
        Log.d(TRACKER_LOG_NAME, "Sample Track Start")
    }

    override fun apiKey(): String = apiKey.toString()

    override fun trackEvent(event: Event) {
        when (event) {
            is Event.EventTrack -> Log.d(
                TRACKER_LOG_NAME,
                "Event Name: ${event.eventName} Properties: ${event.properties}"
            )
            else -> {
            }
        }
    }
}