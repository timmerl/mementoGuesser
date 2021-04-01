package com.timmerl.mementoguesser.presentation.view.editmemento

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.common.MementoEditionWidget
import com.timmerl.mementoguesser.presentation.view.editmemento.EditMementoUiEvent.ActionDone


@Composable
fun EditMementoScreen(
    viewModel: EditMementoViewModel,
    onActionDone: () -> Unit
) {
    val image: String by viewModel.image.observeAsState(initial = "")
    val memory: String by viewModel.memory.observeAsState(initial = "")

    val events = viewModel.event.observeAsState()
    val event = events.value
    if (event?.getEventIfNotHandled() == ActionDone)
        onActionDone()
    else MementoEditionWidget(
        image = image,
        memory = memory,
        actionLabel = "éditer",
        onMemoryChange = viewModel::onMemoryChange,
        onImageChange = viewModel::onImageChange,
        onClick = viewModel::editMemento,
    )
}


@Preview
@Composable
fun AddMementoScreenPreview() {
    MgTheme {
        MementoEditionWidget(
            image = "Image",
            memory = "Memory",
            actionLabel = "éditer",
            onMemoryChange = {},
            onImageChange = {},
            onClick = {},
        )
    }

}