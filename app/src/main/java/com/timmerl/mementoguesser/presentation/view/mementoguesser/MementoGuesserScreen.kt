package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.common.Curtain
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.mementoguesser.MementoGuesserViewModel.MementoGuesserUiEvent.NavigateToAddMemento

@Composable
fun MementoGuesserScreen(
    viewModel: MementoGuesserViewModel,
    navigateToAddMemento: () -> Unit
) {

    val events = viewModel.uiEvent.observeAsState()
    val event = events.value
    if (event?.getEventIfNotHandled() == NavigateToAddMemento)
        navigateToAddMemento()
    else MementoGuesserBaseScreen(
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
//    val animateGuessCard = remember { mutableStateOf(false) }
    when (val card = state.value.cardType) {
        is CardType.Welcome -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                WelcomeCard(onClicked = onWelcomeClicked)
            }
        }
        is CardType.Guess -> {
//            animateGuessCard.value = true
            GuessCard(
//                animate = animateGuessCard,
                answer = card.answer,
                question = card.question,
                countMessage = state.value.countMessage,
                idx = state.value.count,
                onCloseEnds = onCloseEnds
//                onCloseEnds = {
//                    animateGuessCard.value = false
//                    onCloseEnds()
//                }
            )
        }
    }
}

@Composable
fun GuessCard(
//    animate: State<Boolean>,
    answer: String,
    question: String,
    countMessage: String,
    idx: Int,
    onCloseEnds: () -> Unit = {},
) {
//    AnimatedVisibility(
//        visible = animate.value,
//        enter = fadeIn(initialAlpha = 0.0f),
//        exit = fadeOut()
//    ) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Curtain(
            foldingDuration = 400,
            onOpenEnds = {},
            onCloseEnds = onCloseEnds,
            mainCell = {
                QuestionCard(
                    question = question,
                    idx = idx,
                    countMessage = countMessage
                )
            },
            foldCells = listOf(
                { AnswerCard(answer = answer, idx = idx) }
            )
        )
    }
}
//}

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
    countMessage: String,
    idx: Int
) {
    Surface(
        color = MementoGuesserTheme.colors.guesserColors
            .questionBackground[idx % MementoGuesserTheme.colors.guesserColors.questionBackground.size],
        contentColor = MementoGuesserTheme.colors.guesserColors.questionContent,
        modifier = Modifier
            .wrapContentHeight()
            .width(width = 256.dp),
        shape = MaterialTheme.shapes.medium.copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 12.dp, start = 6.dp, top = 4.dp, bottom = 4.dp)
        ) {
            Text(
                text = countMessage,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.End,
                fontSize = 8.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = question,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = MementoGuesserTheme.colors.guesserColors
                    .question[idx % MementoGuesserTheme.colors.guesserColors.question.size]
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Question",
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun AnswerCard(
    answer: String,
    idx: Int
) {
    Surface(
        color = MementoGuesserTheme.colors.guesserColors
            .answerBackground[idx % MementoGuesserTheme.colors.guesserColors.answerBackground.size],
        contentColor = MementoGuesserTheme.colors.guesserColors.answerContent,
        modifier = Modifier
            .size(width = 256.dp, height = 72.dp),
        shape = MaterialTheme.shapes.medium.copy(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(end = 6.dp, start = 6.dp, bottom = 6.dp, top = 2.dp)
        ) {
            Text(
                text = "Réponse",
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = answer,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                color = MementoGuesserTheme.colors.guesserColors
                    .answer[idx % MementoGuesserTheme.colors.guesserColors.answer.size],
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