package com.timmerl.mementoguesser.presentation.view.app

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.timmerl.mementoguesser.presentation.utils.Navigator
import kotlinx.parcelize.Parcelize


sealed class Destination : Parcelable {
    @Immutable
    @Parcelize
    object Guesser : Destination()

    @Immutable
    @Parcelize
    object Management : Destination()

    @Immutable
    @Parcelize
    object AddMemento : Destination()

    @Immutable
    @Parcelize
    data class EditMemento(
        val mementoId: Long,
        val imageId: Long
    ) : Destination()
}

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
    val editMemento: (mementoId: Long, imageId: Long) -> Unit = { mementoId, imageId ->
        navigator.navigate(Destination.EditMemento(mementoId = mementoId, imageId = imageId))
    }
    val upPress: () -> Unit = {
        navigator.back()
    }
}
