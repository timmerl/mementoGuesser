package com.timmerl.mementoguesser.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

inline fun <T> LiveData<UiEvent<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner) { it?.getEventIfNotHandled()?.let(onEventUnhandledContent) }
}