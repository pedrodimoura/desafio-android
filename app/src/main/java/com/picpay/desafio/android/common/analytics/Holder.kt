package com.picpay.desafio.android.common.analytics

open class Holder<out T : Any, AnalyticsBuilder>(creator: (AnalyticsBuilder) -> T) {
    private var creator: ((AnalyticsBuilder) -> T)? = creator

    @Volatile
    private var instance: T? = null


    fun with(builder: AnalyticsBuilder): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(builder)
                instance = created
                creator = null
                created
            }
        }
    }
}