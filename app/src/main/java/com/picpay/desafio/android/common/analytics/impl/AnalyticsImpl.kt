package com.picpay.desafio.android.common.analytics.impl

import com.picpay.desafio.android.common.analytics.Analytics
import com.picpay.desafio.android.common.analytics.Event
import com.picpay.desafio.android.common.analytics.EventBuilder
import com.picpay.desafio.android.common.analytics.Tracker

class AnalyticsImpl(private val trackers: MutableList<Tracker>) : Analytics {
    init {
        trackers.forEach {
            if (it.isAnalyticsEnabled())
                it.start()
        }
    }

    override fun track(setup: EventBuilder.() -> Unit) {
        val eventBuilder = EventBuilder()
        eventBuilder.setup()
        val event = eventBuilder.build()
        event?.let {
            if (it is Event.EventTrack) {
                trackers.forEach { tracker: Tracker ->
                    tracker.trackEvent(it)
                }
            }
        }
    }
}
