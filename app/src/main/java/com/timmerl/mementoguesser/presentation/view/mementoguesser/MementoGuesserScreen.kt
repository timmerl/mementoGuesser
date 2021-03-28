package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.common.MgCard
import com.timmerl.mementoguesser.presentation.common.MgSurface
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun MementoGuesserScreen(
    viewModel: MementoGuesserViewModel
) {
    val state = viewModel.uiModel.observeAsState(MementoGuesserViewModel.defaultUiModel)
    val msg = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val card = state.value.cardType) {
            is CardType.Welcome -> WelcomeCard(
                onClicked = viewModel::onWelcomeCardClicked
            )
            is CardType.Answer -> {
                msg.value = card.answer
                AnswerCard(
                    answer = msg,
                    onClicked = viewModel::onAnswerCardClicked
                )
            }
            is CardType.Question -> {
                msg.value = card.question
                QuestionCard(
                    question = msg,
                    onClicked = viewModel::onQuestionCardClicked
                )
            }
        }
    }
}

@Composable
fun WelcomeCard(
    onClicked: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClicked)
    ) {
        Text(
            text = LocalContext.current.getString(R.string.welcome_message),
            style = MaterialTheme.typography.h4,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun QuestionCard(
    question: State<String>,
    onClicked: () -> Unit = {}
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.question),
        message = question,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors.questionBackground,
            contentColor = MementoGuesserTheme.colors.questionContent,
            labelColor = MementoGuesserTheme.colors.questionLabel,
        ),
        onClicked = onClicked
    )
}

@Composable
fun AnswerCard(
    answer: State<String>,
    onClicked: () -> Unit = {}
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.answer),
        message = answer,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors.answerBackground,
            contentColor = MementoGuesserTheme.colors.answerContent,
            labelColor = MementoGuesserTheme.colors.answerLabel,
        ),
        onClicked = onClicked
    )
}

class GuesserCardState(surfaceColor: Color, contentColor: Color, labelColor: Color) {
    val surfaceColor: Color by mutableStateOf(value = surfaceColor)
    val contentColor: Color by mutableStateOf(value = contentColor)
    val labelColor: Color by mutableStateOf(value = labelColor)
}

@Composable
fun GuesserBaseCard(
    label: String,
    message: State<String>,
    state: GuesserCardState = GuesserCardState(
        surfaceColor = MementoGuesserTheme.colors.surface,
        contentColor = MementoGuesserTheme.colors.onSurface,
        labelColor = MementoGuesserTheme.colors.onSurface
    ),
    onClicked: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .size(width = 256.dp, height = 72.dp)
            .clickable(onClick = onClicked)
    ) {
        Surface(
            color = state.surfaceColor,
            contentColor = state.contentColor,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(6.dp)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.size(height = 20.dp, width = 256.dp),
                    color = state.labelColor
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = message.value,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.size(height = 48.dp, width = 256.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun WelcomeCardPreview() {
    MgTheme {
        WelcomeCard {}
    }
}

@Preview
@Composable
fun QuestionCardPreview() {
    MgTheme {
        QuestionCard(question = mutableStateOf("Comment va ?"))
    }
}

@Preview
@Composable
fun AnswerCardPreview() {
    MgTheme {
        AnswerCard(answer = mutableStateOf("Pépouzz"))
    }
}