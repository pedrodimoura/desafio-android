package com.picpay.desafio.android.common.analytics

import androidx.annotation.StringDef

interface Trackable {
    @EventName
    val eventName: String
    val properties: Map<String, String>?
}

sealed class Event : Trackable {
    data class EventTrack(
        @EventName override val eventName: String,
        override val properties: Map<String, String>?
    ) : Event() {
        companion object
    }

    data class CrashTrack(
        @EventName
        override val eventName: String,
        override val properties: Map<String, String>?
    ) : Event() {
        companion object
    }
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    EventName.PAGE_VIEW,
    EventName.PAGE_NAME,
    EventName.EXCEPTION,
    EventName.STACK_TRACE,
    EventName.PROPERTY_FILE,
    EventName.PROPERTY_LINE_NUMBER,
    EventName.PROPERTY_COLUMN_NUMBER,
    EventName.PROPERTY_METHOD,
    EventName.CAUSE,
    EventName.MESSAGE
)
annotation class EventName {
    companion object {
        const val PAGE_VIEW = "pageView"
        const val PAGE_NAME = "pageName"
        const val EXCEPTION = "exception"
        const val STACK_TRACE = "stack_trace"
        const val CAUSE = "cause"
        const val MESSAGE = "message"

        const val PROPERTY_FILE = "file"
        const val PROPERTY_LINE_NUMBER = "lineNumber"
        const val PROPERTY_COLUMN_NUMBER = "columnNumber"
        const val PROPERTY_METHOD = "method"
    }
}
