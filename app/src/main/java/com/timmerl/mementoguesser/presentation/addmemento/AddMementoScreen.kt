package com.timmerl.mementoguesser.presentation.addmemento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timmerl.mementoguesser.presentation.lightTheme

@Composable
fun AddMementoScreen(
    addMementoViewModel: AddMementoViewModel = viewModel()
) = MaterialTheme(colors = lightTheme) {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
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
    }
}

@Composable
fun AddMementoWidget(
    image: String,
    memory: String,
    onMemoryChange: (String) -> Unit = {},
    onImageChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) = MaterialTheme(colors = lightTheme) {
    val focusRequester = remember { FocusRequester() }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(22.dp))
        MemoryInput(
            input = memory,
            focusRequester = focusRequester,
            onValueChange = onMemoryChange,
        )
        Spacer(modifier = Modifier.height(22.dp))
        ImageInput(
            input = image, onValueChange = onImageChange
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = {
            onClick()
            focusRequester.requestFocus()
        }) {
            Text(text = "Enregistrer", modifier = Modifier.padding(16.dp))
        }
    }
}


@Composable
fun MemoryInput(
    input: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        AutoFocusingInput(
            hint = "Souvenir",
            input = input,
            focusRequester = focusRequester,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun ImageInput(
    input: String,
    onValueChange: (String) -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        AutoFocusingInput(hint = "Image", input = input, onValueChange = onValueChange)
    }
}

@Composable
fun AutoFocusingInput(
    hint: String,
    input: String,
    focusRequester: FocusRequester? = null,
    onValueChange: (String) -> Unit
) {
    val modifier =
        if (focusRequester == null) {
            Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
        } else Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester)

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
    if (focusRequester != null) {
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}

@Composable
fun AddMementoWidgetPreview() {
    AddMementoWidget(
        image = "An Image to save",
        memory = "A Memory to save"
    )
}