package com.timmerl.mementoguesser.presentation.view.app

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize


sealed class Destination : Parcelable {
    @Parcelize
    object Guesser : Destination()

    @Immutable
    @Parcelize
    object Management : Destination()

    @Immutable
    @Parcelize
    object AddMemento : Destination()
}

/**
 * Models the navigation actions in the app.
 */
class Actions(navigator: Navigator<Destination>) {
    val guesser: () -> Unit = {
        navigator.navigate(Destination.Guesser)
    }
    val management: () -> Unit = {
        navigator.navigate(Destination.Management)
    }
    val addMemento: () -> Unit = {
        navigator.navigate(Destination.AddMemento)
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
