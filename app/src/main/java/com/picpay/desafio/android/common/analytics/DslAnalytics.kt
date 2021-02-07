package com.picpay.desafio.android.common.analytics

import com.picpay.desafio.android.common.analytics.impl.AnalyticsImpl

annotation class DslAnalytics

@DslAnalytics
open class EventBuilder(var name: String? = null) {
    val data: MutableMap<String, String>? by lazy {
        mutableMapOf<String, String>()
    }

    fun String.to(value: String) {
        data?.let {
            if (!it.containsKey(this)) {
                it[this] = value
            }
        }
    }

    fun put(key: String, value: String): EventBuilder {
        data?.let {
            if (!it.containsKey(key)) {
                it[key] = value
            }
        }
        return this@EventBuilder
    }

    fun build(): Trackable? {
        if (name.isNullOrBlank()) throw IllegalArgumentException("Events should contain a name look at DISPLAY/EVENT annotation class")
        name?.let {
            return when (it) {
                EventName.PAGE_VIEW -> Event.EventTrack(it, data)
                EventName.EXCEPTION -> Event.CrashTrack(it, data)
                else -> {
                    null
                }
            }
        }
        return null
    }
}

fun analytics(initializer: AnalyticsBuilder.() -> Unit = {}): Analytics =
    Analytics.with(initializer)

@DslAnalytics
class AnalyticsBuilder {

    private val trackers = mutableListOf<Tracker>()

    fun kit(tracker: Tracker) = apply {
        trackers += tracker
    }

    fun build() = AnalyticsImpl(trackers = trackers)
}
