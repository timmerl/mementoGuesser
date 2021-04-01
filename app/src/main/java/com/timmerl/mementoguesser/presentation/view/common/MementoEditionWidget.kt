package com.timmerl.mementoguesser.presentation.view.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MementoEditionWidget(
    image: String,
    memory: String,
    actionLabel: String,
    onMemoryChange: (String) -> Unit = {},
    onImageChange: (String) -> Unit = {},
    onClick: () -> Unit = {}
) {

    val imageFocus = FocusRequester()
    val memoryFocus = FocusRequester()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(22.dp))
        MemoryInput(
            input = memory,
            onValueChange = onMemoryChange,
            focusRequester = memoryFocus,
            onNext = { imageFocus.requestFocus() }
        )
        Spacer(modifier = Modifier.height(22.dp))
        ImageInput(
            input = image,
            focusRequester = imageFocus,
            onValueChange = onImageChange,
            onDone = {
                onClick()
                memoryFocus.requestFocus()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(onClick = {
            onClick()
            memoryFocus.requestFocus()
        }) {
            Text(text = actionLabel, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun MemoryInput(
    input: String,
    focusRequester: FocusRequester?,
    onValueChange: (String) -> Unit,
    onNext: (KeyboardActionScope.() -> Unit)
) {
    val modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxWidth()
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Souvenir") },
        modifier = focusRequester?.let {
            modifier.focusRequester(focusRequester = it)
        } ?: modifier,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = onNext),
        singleLine = true
    )
}

@Composable
fun ImageInput(
    input: String,
    focusRequester: FocusRequester?,
    onValueChange: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)
) {
    val modifier = Modifier
        .padding(all = 16.dp)
        .fillMaxWidth()
    TextField(
        value = input,
        onValueChange = onValueChange,
        placeholder = { Text(text = "Image") },
        modifier = focusRequester?.let {
            modifier.focusRequester(focusRequester = it)
        } ?: modifier,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDone),
        singleLine = true
    )
}