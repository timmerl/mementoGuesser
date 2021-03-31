package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.timmerl.mementoguesser.presentation.common.CurtainState.CLOSED
import com.timmerl.mementoguesser.presentation.common.CurtainState.OPENED
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
        onAnswerClicked = viewModel::showNextMemento,
        onQaClicked = viewModel::onQaModeButtonClick,
        onSortClicked = viewModel::onSortButtonCLick
    )
}

@ExperimentalAnimationApi
@Composable
fun MementoGuesserBaseScreen(
    state: State<MementoGuesserUiModel>,
    onAnswerClicked: () -> Unit = {},
    onWelcomeClicked: () -> Unit = {},
    onQaClicked: () -> Unit = {},
    onSortClicked: () -> Unit = {}
) {
    val isGuessCardVisible = remember { mutableStateOf(false) }
    val openCurtainState = remember { mutableStateOf(CLOSED) }

    fun onButtonClicked(onClicked: () -> Unit) {
        openCurtainState.value = CLOSED
        isGuessCardVisible.value = false
        onClicked()
    }
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
                openCurtainState.value = CLOSED
                Button(
                    onClick = { onButtonClicked(onQaClicked) },
                    modifier = Modifier.constrainAs(qaButton) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }) { Text(text = LocalContext.current.getString(state.value.switchQAButtonText)) }
                Button(
                    onClick = { onButtonClicked(onSortClicked) },
                    modifier = Modifier.constrainAs(sortButton) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }) { Text(text = LocalContext.current.getString(state.value.sortButtonText)) }
                GuessCard(
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                if (openCurtainState.value == CLOSED) {
                                    openCurtainState.value = OPENED
                                } else {
                                    openCurtainState.value = CLOSED
                                    isGuessCardVisible.value = false
                                    onAnswerClicked()
                                }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                        .constrainAs(guessCard) {
                            linkTo(
                                top = parent.top,
                                start = parent.start,
                                end = parent.end,
                                bottom = qaButton.top
                            )
                        },
                    openCurtainState = openCurtainState,
                    visible = isGuessCardVisible,
                    answer = card.answer,
                    question = card.question,
                    countMessage = state.value.countMessage,
                    idx = state.value.count,
                    onCloseEnds = {},
                    onOpenEnds = {}
                )
            }
        }
    }
}

