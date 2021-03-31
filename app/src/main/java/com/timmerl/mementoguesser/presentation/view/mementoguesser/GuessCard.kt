package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timmerl.mementoguesser.presentation.common.Curtain
import com.timmerl.mementoguesser.presentation.common.CurtainState
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

@ExperimentalAnimationApi
@Composable
fun GuessCard(
    visible: State<Boolean>,
    openCurtainState: State<CurtainState>,
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
            stateFromOutside = openCurtainState.value,
            foldingDuration = 200,
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
fun AnswerCardPreview() {
    MgTheme {
        GuessCard(
            visible = mutableStateOf(true),
            openCurtainState = mutableStateOf(CurtainState.OPENED),
            answer = "Answer",
            question = "Question",
            countMessage = "osef du compte",
            idx = 0,
            onCloseEnds = {},
            onOpenEnds = {},
            modifier = Modifier
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun QuestionCardPreview() {
    MgTheme {
        GuessCard(
            visible = mutableStateOf(true),
            openCurtainState = mutableStateOf(CurtainState.CLOSED),
            answer = "Answer",
            question = "Question",
            countMessage = "osef du compte",
            idx = 0,
            onCloseEnds = {},
            onOpenEnds = {},
            modifier = Modifier
        )
    }
}
