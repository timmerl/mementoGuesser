package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.addquestion.AddMementoViewModel
import com.timmerl.mementoguesser.presentation.lightTheme

@Composable
fun AddMementoScreen(
    viewModel: AddMementoViewModel
) = MaterialTheme(colors = lightTheme) {
    Scaffold {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val imageInput = remember { mutableStateOf(TextFieldValue()) }
            val memoryInput = remember { mutableStateOf(TextFieldValue()) }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                MemoryInput(memoryInput)
                Spacer(modifier = Modifier.height(22.dp))
                ImageInput(imageInput)
                Spacer(modifier = Modifier.height(18.dp))
                Button(onClick = {
                    viewModel.createMemento(
                        memory = memoryInput.value.text,
                        image = imageInput.value.text
                    )
                }) {
                    Text(text = "Enregistrer", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun MemoryInput(input: MutableState<TextFieldValue>) {
    Surface(
        color = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface
    ) {
        Input(hint = "Souvenir", input = input)
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
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
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