package com.timmerl.mementoguesser.presentation.view.addmemento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timmerl.mementoguesser.presentation.theme.MgTheme


@Composable
fun AddMementoScreen(
    addMementoViewModel: AddMementoViewModel = viewModel()
) {
    val image: String by addMementoViewModel.image.observeAsState(initial = "")
    val memory: String by addMementoViewModel.memory.observeAsState(initial = "")
    AddMementoWidget(
        image = image,
        memory = memory,
        onMemoryChange = addMementoViewModel::onMemoryChange,
        onImageChange = addMementoViewModel::onImageChange,
        onClick = addMementoViewModel::createMemento
    )
}


@Composable
fun AddMementoWidget(
    image: String,
    memory: String,
    onMemoryChange: (String) -> Unit = {},
    onImageChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(22.dp))
        MemoryInput(
            input = memory,
            onValueChange = onMemoryChange,
        )
        Spacer(modifier = Modifier.height(22.dp))
        ImageInput(
            input = image, onValueChange = onImageChange
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = {
            onClick()
        }) {
            Text(text = "Enregistrer", modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun MemoryInput(
    input: String,
    onValueChange: (String) -> Unit
) {
    MgTextField(
        hint = "Souvenir",
        input = input,
        onValueChange = onValueChange
    )
}

@Composable
fun ImageInput(
    input: String,
    onValueChange: (String) -> Unit
) {
    MgTextField(
        hint = "Image",
        input = input,
        onValueChange = onValueChange
    )
}

@Composable
fun MgTextField(
    hint: String,
    input: String,
    onValueChange: (String) -> Unit
) {
    val modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxWidth()

    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )
}

@Preview
@Composable
fun AddMementoScreenPreview() {
    MgTheme {
        AddMementoWidget(
            image = "Image",
            memory = "Memory",
            onMemoryChange = {},
            onImageChange = {},
            onClick = {})
    }

}