package com.timmerl.mementoguesser.presentation.view.addmemento

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.common.MementoEditionWidget


@Composable
fun AddMementoScreen(
    viewModel: AddMementoViewModel
) {
    val image: String by viewModel.image.observeAsState(initial = "")
    val memory: String by viewModel.memory.observeAsState(initial = "")

    MementoEditionWidget(
        image = image,
        memory = memory,
        actionLabel = "Enregistrer",
        onMemoryChange = viewModel::onMemoryChange,
        onImageChange = viewModel::onImageChange,
        onClick = viewModel::createMemento,
    )
}


@Preview
@Composable
fun AddMementoScreenPreview() {
    MgTheme {
        MementoEditionWidget(
            image = "Image",
            memory = "Memory",
            actionLabel = "Enregistrer",
            onMemoryChange = {},
            onImageChange = {},
            onClick = {},
        )
    }

}