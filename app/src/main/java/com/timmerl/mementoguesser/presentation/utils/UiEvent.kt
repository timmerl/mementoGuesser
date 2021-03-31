package com.timmerl.mementoguesser.presentation.utils

data class UiEvent<out T>(val event: T) {
    var hasBeenHandled = false

    fun getEventIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            event
        }

    companion object {
        fun <T> create(event: T) = UiEvent(event)
    }
}