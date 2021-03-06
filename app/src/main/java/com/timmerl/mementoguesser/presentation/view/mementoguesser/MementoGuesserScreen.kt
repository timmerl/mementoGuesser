package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.common.Curtain
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@Composable
fun MementoGuesserScreen(
    viewModel: MementoGuesserViewModel
) {
    MementoGuesserBaseScreen(
        state = viewModel.uiModel.observeAsState(
            MementoGuesserViewModel.defaultUiModel
        ),
        onWelcomeClicked = viewModel::onWelcomeCardClicked,
        onCloseEnds = viewModel::onShowNextMemento,
    )
}

@Composable
fun MementoGuesserBaseScreen(
    state: State<MementoGuesserUiModel>,
    onCloseEnds: () -> Unit = {},
    onWelcomeClicked: () -> Unit = {}
) {

    when (val card = state.value.cardType) {
        is CardType.Welcome -> Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            WelcomeCard(onClicked = onWelcomeClicked)
        }
        is CardType.Guess -> Surface(modifier = Modifier.fillMaxSize()) {
            Curtain(
                foldingDuration = 400,
                onOpenEnds = {}, onCloseEnds = onCloseEnds,
                mainCell = {
                    QuestionCard(
                        question = card.question,
                    )
                },
                foldCells = listOf(
                    { AnswerCard(answer = card.answer) }
                )
            )
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
    question: String
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.question),
        message = question,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors.questionBackground,
            contentColor = MementoGuesserTheme.colors.questionContent,
            labelColor = MementoGuesserTheme.colors.questionLabel,
        )
    )
}

@Composable
fun AnswerCard(
    answer: String
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.answer),
        message = answer,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors.answerBackground,
            contentColor = MementoGuesserTheme.colors.answerContent,
            labelColor = MementoGuesserTheme.colors.answerLabel,
        )
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
    message: String,
    state: GuesserCardState = GuesserCardState(
        surfaceColor = MementoGuesserTheme.colors.surface,
        contentColor = MementoGuesserTheme.colors.onSurface,
        labelColor = MementoGuesserTheme.colors.onSurface
    )
) {
    Surface(
        color = state.surfaceColor,
        contentColor = state.contentColor,
        modifier = Modifier
            .size(width = 256.dp, height = 72.dp),
        shape = MaterialTheme.shapes.medium
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
    MgTheme {
        MementoGuesserBaseScreen(
            mutableStateOf(
                MementoGuesserUiModel(
                    cardType = CardType.Welcome,
                    count = "", sortButtonText = 0, switchQAButtonText = 0
                )
            )
        )
    }
}

@Preview
@Composable
fun QuestionCardPreview() {
    MgTheme {
        MementoGuesserBaseScreen(
            mutableStateOf(
                MementoGuesserUiModel(
                    cardType = CardType.Guess(
                        question = "Question",
                        answer = "Answer",
                        curtainIsOpen = false
                    ),
                    count = "28", sortButtonText = 0, switchQAButtonText = 0
                )
            )
        )
    }
}

@Preview
@Composable
fun AnswerCardPreview() {
    MgTheme {
        mutableStateOf(
            MementoGuesserUiModel(
                cardType = CardType.Guess(
                    question = "Question",
                    answer = "Answer",
                    curtainIsOpen = true
                ),
                count = "28", sortButtonText = 0, switchQAButtonText = 0
            )
        )
    }
}