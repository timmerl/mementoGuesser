package com.timmerl.mementoguesser.presentation.composable

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
import com.timmerl.mementoguesser.presentation.addquestion.AddMementoViewModel
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
                    addMementoViewModel::onMemoryChange
                )
                Spacer(modifier = Modifier.height(22.dp))
                ImageInput(input = image, addMementoViewModel::onImageChange)
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = {
                        addMementoViewModel.createMemento()
                        focusRequester.requestFocus()
                    }
                ) {
                    Text(text = "Enregistrer", modifier = Modifier.padding(16.dp))
                }
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
        Input(hint = "Image", input = input, onValueChange = onValueChange)
    }
}

@Composable
fun AutoFocusingInput(
    hint: String,
    input: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = true
    )

    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }
}

@Composable
fun Input(
    hint: String,
    input: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

//@Preview(showBackground = true)
//@Composable
//fun MemoryInputPreview() {
//    Input(hint = "Souvenir", mutableStateOf(TextFieldValue()))
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ImageInputPreview() {
//    Input(hint = "Image", mutableStateOf(TextFieldValue()))
//}