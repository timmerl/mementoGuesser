package com.timmerl.mementoguesser.presentation.view.editmemento

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.common.MementoEditionWidget
import com.timmerl.mementoguesser.presentation.view.editmemento.EditMementoUiEvent.ActionDone

/* TODO pb with viewModel : we always get the same instance.
    we need a new one each time a new EditMementoScreen is composed
    (source issue): https://github.com/InsertKoinIO/koin/issues/501
    (google issue): https://issuetracker.google.com/issues/165642391
    (resolve) https://github.com/android/compose-samples/pull/116/commits/80a400ab9ea615a5f2bb5b90166951d97ed08ec3
*/
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