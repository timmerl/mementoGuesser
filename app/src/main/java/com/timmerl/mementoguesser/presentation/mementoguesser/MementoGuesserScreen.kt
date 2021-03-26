package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme

@Composable
fun MementoGuesserScreen(
    viewModel: MementoGuesserViewModel
) {
    val state = viewModel.uiModel.observeAsState(MementoGuesserViewModel.defaultUiModel)
    MaterialTheme(colors = lightTheme) {
        Scaffold() {
            GuesserCard(
                guess = state.value.guessMode.message.resolve(
                    context = LocalContext.current
                ),
                onClicked = viewModel::onGuesserCardClicked
            )
        }
    }
}

@Composable
fun GuesserCard(guess: String, onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(18.dp)
            .clickable(onClick = onClicked),
//        backgroundColor = state.backgroundColor,
//        contentColor = state.contentColor,
//        shape = MaterialTheme.shapes.large
    ) {
        Text(
            text = guess,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}