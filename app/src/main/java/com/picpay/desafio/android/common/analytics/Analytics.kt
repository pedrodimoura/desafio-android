package com.picpay.desafio.android.common.analytics

interface Analytics {
    fun track(setup: EventBuilder.() -> Unit = {})

    companion object :
        Holder<Analytics, AnalyticsBuilder.() -> Unit>(
            { builder: AnalyticsBuilder.() -> Unit ->
                AnalyticsBuilder().apply(builder).build()
            })
}