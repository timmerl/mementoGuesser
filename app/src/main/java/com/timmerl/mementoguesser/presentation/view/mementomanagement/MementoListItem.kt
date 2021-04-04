package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.common.horizontalGradientBackground
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MementoListItemColorState(
    questionBackgroundColor: Color,
    answerBackgroundColor: Color,
    contentColor: Color,
) {
    val questionBackgroundColor: Color by mutableStateOf(value = questionBackgroundColor)
    val answerBackgroundColor: Color by mutableStateOf(value = answerBackgroundColor)
    val contentColor: Color by mutableStateOf(value = contentColor)
}

@Composable
fun itemDefaultColor(idx: Int, isPlayable: Boolean): MementoListItemColorState {
    return if (isPlayable) {
        MementoListItemColorState(
            questionBackgroundColor = MgTheme.colors.questionBackground(idx),
            answerBackgroundColor = MgTheme.colors.answerBackground(idx),
            contentColor = MgTheme.colors.questionContent(idx),
        )
    } else {
        MementoListItemColorState(
            questionBackgroundColor = MgTheme.colors.error,
            answerBackgroundColor = MgTheme.colors.error,
            contentColor = contentColorFor(backgroundColor = MgTheme.colors.error),
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MementoListItem(
    memory: String,
    image: String,
    onClick: () -> Unit,
    onRemoveClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
    state: MementoListItemColorState,
) {
    val itemMenuCount = 2
    val contentHeight = 45.dp
    val imageHeight = 35.dp
    val contentWidth = 150.dp
    val swipeState = rememberSwipeableState(initialValue = 0)
    val swipeDistancePx = with(LocalDensity.current) { contentHeight.toPx() * itemMenuCount }
    val anchors: Map<Float, Int> = mapOf(0f to 0, swipeDistancePx to 1)
    val exists = remember { mutableStateOf(true) }
    exists.value = true
    AnimatedVisibility(
        visible = exists.value,
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .width(contentWidth)
                .height(contentHeight)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .swipeable(
                        state = swipeState,
                        anchors = anchors,
                        orientation = Orientation.Horizontal,
                        thresholds = { from, to -> FractionalThreshold(0.3f) },
                    )
            ) {
                MementoListItemMenu(
                    exists = exists,
                    swipeState = swipeState,
                    imageHeight = imageHeight,
                    onEditClicked = onEditClicked,
                    onRemoveClicked = onRemoveClicked
                )
                MementoListItemSlider(
                    swipeState = swipeState,
                    state = state,
                    memory = memory,
                    image = image,
                    onClick = onClick
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BoxScope.MementoListItemMenu(
    exists: MutableState<Boolean>,
    swipeState: SwipeableState<Int>,
    imageHeight: Dp,
    onEditClicked: () -> Unit,
    onRemoveClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .wrapContentSize()
            .align(Alignment.CenterStart)
            .padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_edit),
            contentDescription = null,
            modifier = Modifier
                .size(imageHeight)
                .clickable(onClick = onEditClicked),
        )
        Image(
            painter = painterResource(id = android.R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier
                .size(imageHeight)
                .clickable(onClick = {
                    scope.launch {
                        exists.value = false
                        swipeState.animateTo(0)
                        onRemoveClicked()
                    }
                }),
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun BoxScope.MementoListItemSlider(
    swipeState: SwipeableState<Int>,
    state: MementoListItemColorState,
    memory: String,
    image: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = MutableInteractionSource()
            )
            .horizontalGradientBackground(
                listOf(
                    state.questionBackgroundColor,
                    state.answerBackgroundColor
                )
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MemoryCard(
            message = memory, contentColor = state.contentColor,
        )
        ImageCard(
            message = image, contentColor = state.contentColor
        )
    }
}

@Composable
fun MemoryCard(
    message: String,
    contentColor: Color
) {
    Text(
        text = message,
        color = contentColor,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(start = 6.dp),
        textAlign = TextAlign.Start

    )
}

@Composable
fun ImageCard(
    message: String,
    contentColor: Color
) {
    Text(
        text = message,
        color = contentColor,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
            .padding(end = 6.dp),
        textAlign = TextAlign.End
    )
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun PlayableMementoCarPreview() {
    MgTheme {
        MementoListItem(
            memory = "12",
            image = "Image un peu longue",
            state = MementoListItemColorState(
                questionBackgroundColor = MgTheme.colors.questionBackground(1),
                answerBackgroundColor = MgTheme.colors.answerBackground(1),
                contentColor = MgTheme.colors.question(1)
            ), onRemoveClicked = {},
            onClick = {}
        )
    }
}


@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun NonPlayableMementoCardPreview() {
    MgTheme {
        MementoListItem(
            memory = "12",
            image = "image",
            state = MementoListItemColorState(
                questionBackgroundColor = MgTheme.colors.surfaceNotAvailable,
                answerBackgroundColor = MgTheme.colors.surfaceNotAvailable,
                contentColor = MgTheme.colors.onSurfaceNotAvailable
            ),
            onRemoveClicked = {}, onClick = {}
        )
    }
}

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class MementoListItemUiModel(
    val mementoId: Long,
    val imageId: Long,
    val memory: String,
    val image: String,
    val isPlayable: Boolean
)