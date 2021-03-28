package com.timmerl.mementoguesser.presentation.view.addmemento

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timmerl.mementoguesser.presentation.common.MgButton
import com.timmerl.mementoguesser.presentation.common.MgScaffold
import com.timmerl.mementoguesser.presentation.common.MgSurface
import com.timmerl.mementoguesser.presentation.common.MgTextField
import com.timmerl.mementoguesser.presentation.theme.MgTheme


@Composable
fun AddMementoScreen(
    addMementoViewModel: AddMementoViewModel = viewModel()
) {
    val image: String by addMementoViewModel.image.observeAsState(initial = "")
    val memory: String by addMementoViewModel.memory.observeAsState(initial = "")
    AddMementoBaseScreen(
        image = image,
        memory = memory,
        onMemoryChange = addMementoViewModel::onMemoryChange,
        onImageChange = addMementoViewModel::onImageChange,
        onClick = addMementoViewModel::createMemento
    )
}

@Composable
fun AddMementoBaseScreen(
    image: String,
    memory: String,
    onMemoryChange: (String) -> Unit = {},
    onImageChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {
    MgTheme {
        MgScaffold {
            MgSurface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                AddMementoWidget(
                    image = image,
                    memory = memory,
                    onMemoryChange = onMemoryChange,
                    onImageChange = onImageChange,
                    onClick = onClick
                )
            }
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
) {
    MgTheme {
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
            MgButton(onClick = {
                onClick()
                focusRequester.requestFocus()
            }) {
                Text(text = "Enregistrer", modifier = Modifier.padding(16.dp))
            }
        }
    }
}


@Composable
fun MemoryInput(
    input: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit
) {
    MgSurface {
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
    MgSurface {
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

    MgTextField(
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
        DisposableEffect("autofocus") {
            focusRequester.requestFocus()
            onDispose { }
        }
    }
}

@Preview
@Composable
fun AddMementoScreenPreview() {
    AddMementoBaseScreen(
        image = "Image",
        memory = "Memory",
        onMemoryChange = {},
        onImageChange = {},
        onClick = {})
}