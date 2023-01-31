package com.sugo.app.feat.ui.common

import androidx.lifecycle.Observer

/**
 * reference link
 * https://github.com/android/architecture-samples/blob/main/app/src/main/java/com/example/android/architecture/blueprints/todoapp/Event.kt
 */
class Event<T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}