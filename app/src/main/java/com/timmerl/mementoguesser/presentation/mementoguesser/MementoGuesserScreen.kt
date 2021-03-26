package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.appTheme


@Composable
fun MementoGuesserScreen(
    viewModel: MementoGuesserViewModel
) {
    val state = viewModel.uiModel.observeAsState(MementoGuesserViewModel.defaultUiModel)
    MaterialTheme(colors = appTheme) {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                when (val card = state.value.cardType) {
                    is CardType.Welcome -> WelcomeCard(
                        onClicked = viewModel::onWelcomeCardClicked
                    )
                    is CardType.Answer -> AnswerCard(
                        answer = card.answer,
                        backgroundColor = card.backgroundColor,
                        contentColor = card.contentColor,
                        labelColor = card.labelColor,
                        onClicked = viewModel::onAnswerCardClicked
                    )
                    is CardType.Question -> QuestionCard(
                        question = card.question,
                        backgroundColor = card.backgroundColor,
                        contentColor = card.contentColor,
                        labelColor = card.labelColor,
                        onClicked = viewModel::onQuestionCardClicked
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(
    onClicked: () -> Unit,
) {
    Card(
        backgroundColor = appTheme.surface,
        contentColor = appTheme.onSurface,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .clickable(onClick = onClicked)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = LocalContext.current.getString(R.string.welcome_message),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Composable
fun QuestionCard(
    question: String,
    backgroundColor: Color = appTheme.primary,
    contentColor: Color = contentColorFor(backgroundColor = backgroundColor),
    labelColor: Color = contentColorFor(backgroundColor = backgroundColor),
    onClicked: () -> Unit = {}
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.question),
        message = question,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        labelColor = labelColor,
        onClicked = onClicked
    )
}

@Composable
fun AnswerCard(
    answer: String,
    backgroundColor: Color = appTheme.secondary,
    contentColor: Color = contentColorFor(backgroundColor = backgroundColor),
    labelColor: Color = contentColorFor(backgroundColor = backgroundColor),
    onClicked: () -> Unit = {}
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.answer),
        message = answer,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        labelColor = labelColor,
        onClicked = onClicked
    )
}

@Composable
fun GuesserBaseCard(
    label: String,
    message: String,
    backgroundColor: Color,
    contentColor: Color,
    labelColor: Color,
    onClicked: () -> Unit = {}
) {
    Card(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier
            .size(width = 256.dp, height = 72.dp)
            .clickable(onClick = onClicked)
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
                color = labelColor
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.size(height = 48.dp, width = 256.dp)
            )
        }
    }
}

@Preview
@Composable
fun WelcomeCardPreview() {
    WelcomeCard {}
}

@Preview
@Composable
fun QuestionCardPreview() {
    QuestionCard(question = "Comment va ?")
}

@Preview
@Composable
fun AnswerCardPreview() {
    AnswerCard(answer = "Pépouzz")
}