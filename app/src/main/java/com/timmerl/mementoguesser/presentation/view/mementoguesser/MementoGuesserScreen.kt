package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
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
                        idx = state.value.count
                    )
                },
                foldCells = listOf(
                    { AnswerCard(answer = card.answer, idx = state.value.count) }
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
    question: String,
    idx: Int
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.question),
        message = question,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors
                .questionBackground[idx % MementoGuesserTheme.colors.questionBackground.size],
            contentColor = MementoGuesserTheme.colors
                .questionContent[idx % MementoGuesserTheme.colors.questionContent.size],
        ),
        roundTop = true,
        roundBottom = false
    )
}

@Composable
fun AnswerCard(
    answer: String,
    idx: Int
) {
    GuesserBaseCard(
        label = LocalContext.current.getString(R.string.answer),
        message = answer,
        state = GuesserCardState(
            surfaceColor = MementoGuesserTheme.colors
                .answerBackground[idx % MementoGuesserTheme.colors.answerBackground.size],
            contentColor = MementoGuesserTheme.colors
                .answerContent[idx % MementoGuesserTheme.colors.answerContent.size]
        ),
        roundTop = false,
        roundBottom = true
    )
}

class GuesserCardState(surfaceColor: Color, contentColor: Color) {
    val surfaceColor: Color by mutableStateOf(value = surfaceColor)
    val contentColor: Color by mutableStateOf(value = contentColor)
}

@Composable
fun GuesserBaseCard(
    label: String,
    message: String,
    state: GuesserCardState,
    roundTop: Boolean,
    roundBottom: Boolean
) {
    Surface(
        color = state.surfaceColor,
        contentColor = state.contentColor,
        modifier = Modifier
            .size(width = 256.dp, height = 72.dp),
        shape = MaterialTheme.shapes.medium.copy(
            topStart = if (roundTop) MaterialTheme.shapes.medium.topStart else CornerSize(0.dp),
            topEnd = if (roundTop) MaterialTheme.shapes.medium.topEnd else CornerSize(0.dp),
            bottomEnd = if (roundBottom) MaterialTheme.shapes.medium.bottomEnd else CornerSize(0.dp),
            bottomStart = if (roundBottom) MaterialTheme.shapes.medium.bottomStart else CornerSize(0.dp)
        )
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
                color = state.contentColor
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
                    countMessage = "", count = 0, sortButtonText = 0, switchQAButtonText = 0
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
                    countMessage = "28", count = 28, sortButtonText = 0, switchQAButtonText = 0
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
                countMessage = "28", count = 28, sortButtonText = 0, switchQAButtonText = 0
            )
        )
    }
}