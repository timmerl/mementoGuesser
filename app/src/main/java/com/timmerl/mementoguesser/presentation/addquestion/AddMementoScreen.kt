package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme

@Composable
fun AddMementoScreen(
    onAction: (String, String) -> Unit
) = MaterialTheme(colors = lightTheme) {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val imageInput = mutableStateOf(TextFieldValue())
            val memoryInput = mutableStateOf(TextFieldValue())
            val focusRequester = remember { FocusRequester() }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                MemoryInput(memoryInput, focusRequester)
                Spacer(modifier = Modifier.height(22.dp))
                ImageInput(imageInput)
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = {
                        onAction(memoryInput.value.text, imageInput.value.text)
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
fun MemoryInput(input: MutableState<TextFieldValue>, focusRequester: FocusRequester) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        AutoFocusingInput(hint = "Souvenir", input = input, focusRequester = focusRequester)
    }
}

@Composable
fun ImageInput(input: MutableState<TextFieldValue>) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Input(hint = "Image", input = input)
    }
}

@Composable
fun AutoFocusingInput(
    hint: String,
    input: MutableState<TextFieldValue>,
    focusRequester: FocusRequester
) {
    TextField(
        value = input.value,
        onValueChange = { input.value = it },
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
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
fun Input(hint: String, input: MutableState<TextFieldValue>) {
    TextField(
        value = input.value,
        onValueChange = { input.value = it },
        placeholder = { Text(text = hint) },
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun MemoryInputPreview() {
    Input(hint = "Souvenir", mutableStateOf(TextFieldValue()))
}

@Preview(showBackground = true)
@Composable
fun ImageInputPreview() {
    Input(hint = "Image", mutableStateOf(TextFieldValue()))
}