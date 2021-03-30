package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.common.Curtain
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.mementoguesser.MementoGuesserViewModel.MementoGuesserUiEvent.NavigateToAddMemento

@ExperimentalAnimationApi
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
        onQaClicked = viewModel::onQaModeButtonClick,
        onSortClicked = viewModel::onSortButtonCLick,
        onOpenEnds = viewModel::onShowAnswer
    )
}

@ExperimentalAnimationApi
@Composable
fun MementoGuesserBaseScreen(
    state: State<MementoGuesserUiModel>,
    onCloseEnds: () -> Unit = {},
    onWelcomeClicked: () -> Unit = {},
    onQaClicked: () -> Unit = {},
    onSortClicked: () -> Unit = {},
    onOpenEnds: () -> Unit = {},
) {
    val isGuessCardVisible = remember { mutableStateOf(false) }
    when (val card = state.value.cardType) {
        is CardType.Welcome -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                WelcomeCard(onClicked = onWelcomeClicked)
            }
        }
        is CardType.Guess -> {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
            ) {
                val (qaButton, sortButton, guessCard) = createRefs()
                isGuessCardVisible.value = true
                Button(
                    onClick = {
                        isGuessCardVisible.value = false
                        onQaClicked()
                    },
                    modifier = Modifier.constrainAs(qaButton) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }) { Text(text = "q/a") }
                Button(
                    onClick = {
                        isGuessCardVisible.value = false
                        onSortClicked()
                    },
                    modifier = Modifier.constrainAs(sortButton) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }) { Text(text = "sort") }
                GuessCard(
                    modifier = Modifier.constrainAs(guessCard) {
                        linkTo(
                            top = qaButton.bottom,
                            start = parent.start,
                            end = parent.end,
                            bottom = parent.bottom
                        )
                    },
                    visible = isGuessCardVisible,
                    answer = card.answer,
                    question = card.question,
                    countMessage = state.value.countMessage,
                    idx = state.value.count,
                    onCloseEnds = {
                        isGuessCardVisible.value = false
                        onCloseEnds()
                    },
                    onOpenEnds = onOpenEnds
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun GuessCard(
    visible: State<Boolean>,
    answer: String,
    question: String,
    countMessage: String,
    idx: Int,
    onCloseEnds: () -> Unit = {},
    onOpenEnds: () -> Unit = {},
    modifier: Modifier,
) {
    AnimatedVisibility(
        visible = visible.value,
        enter = slideInHorizontally(initialOffsetX = { size -> -size })
                + fadeIn(initialAlpha = 0.0f),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Curtain(
            foldingDuration = 400,
            onOpenEnds = onOpenEnds,
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

@ExperimentalAnimationApi
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

@ExperimentalAnimationApi
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