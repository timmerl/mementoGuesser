package com.timmerl.mementoguesser.presentation.common

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class CurtainState {
    OPENED,
    CLOSED,
}

fun CurtainState.next() = when (this) {
    CurtainState.OPENED -> CurtainState.CLOSED
    CurtainState.CLOSED -> CurtainState.OPENED
}

@Composable
fun Curtain(
    stateFromOutside: CurtainState? = null,
    foldingDuration: Int = 250,
    onOpenEnds: () -> Unit,
    onCloseEnds: () -> Unit,
    mainCell: @Composable () -> Unit,
    foldCells: List<@Composable () -> Unit>
) {
    var openState by remember { mutableStateOf(CurtainState.CLOSED) }
    var isTransitionRunning by remember { mutableStateOf(false) }
    val foldScope = rememberCoroutineScope()

    fun toggleCurtain() {
        if (!isTransitionRunning) {
            isTransitionRunning = true
            openState = openState.next()
            val isOpen = openState == CurtainState.OPENED

            foldScope.launch {
                delay(foldingDuration.toLong() * foldCells.size)
                isTransitionRunning = false
                if (isOpen)
                    onOpenEnds()
                else onCloseEnds()
            }
        }
    }

    if (stateFromOutside != null) {
        openState = stateFromOutside
    }

    Column(
        modifier = Modifier.curtainModifier(
            stateFromOutside != null,
            onClick = { toggleCurtain() }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainCell(
            content = mainCell
        )
        FoldedCells(
            openState = openState,
            foldingDuration = foldingDuration,
            foldCells = foldCells
        )
    }
}

private fun Modifier.curtainModifier(
    externalControl: Boolean = false,
    onClick: () -> Unit
): Modifier {
    return if (!externalControl)
        wrapContentSize()
            .clickable(onClick = onClick)
    else wrapContentSize()
}

@Composable
private fun MainCell(
    content: @Composable () -> Unit
) = Box { content() }

@Composable
private fun FoldedCells(
    openState: CurtainState,
    foldingDuration: Int,
    foldCells: List<@Composable () -> Unit>
) {
    Column {
        foldCells.forEachIndexed { index, cell ->
            FoldedCell(
                openState = openState,
                cellsQuantity = foldCells.size,
                foldingDuration = foldingDuration,
                index = index,
                content = cell
            )
        }
    }
}

@Composable
private fun FoldedCell(
    openState: CurtainState,
    cellsQuantity: Int,
    foldingDuration: Int,
    index: Int,
    content: @Composable () -> Unit
) {
    val isOpened = openState == CurtainState.OPENED
    var cellMaxHeight by remember { mutableStateOf(0.dp) }
    val transition = updateTransition(targetState = isOpened, label = "foldedCellIsOpenTransition")
    val foldingDelay =
        if (isOpened) {
            foldingDuration * index
        } else foldingDuration * (cellsQuantity - index)

    val rotationValue by transition.animateFloat(transitionSpec = {
        tween(
            durationMillis = foldingDuration,
            delayMillis = foldingDelay
        )
    }, label = "curtainRotationTransition") { state ->
        when (state) {
            false -> 180f
            true -> 0f
        }
    }
    val alphaValue by transition.animateFloat(transitionSpec = {
        tween(
            durationMillis = foldingDuration,
            delayMillis = foldingDelay
        )
    }, label = "curtainAlphaTransition") { state ->
        when (state) {
            false -> 0f
            true -> 1f
        }
    }
    val sizeValue by transition.animateFloat(transitionSpec = {
        tween(
            durationMillis = foldingDuration,
            delayMillis = foldingDelay
        )
    }, label = "curtainSizeTransition") { state ->
        when (state) {
            false -> 0.dp.value
            true -> cellMaxHeight.value
        }
    }

    Layout(
        content = content,
        modifier = Modifier
            .graphicsLayer {
                alpha = alphaValue
                rotationX = rotationValue
            }
    ) { measurables, constraints ->
        val contentPlaceable = measurables[0].measure(constraints)
        cellMaxHeight = contentPlaceable.height.dp
        layout(contentPlaceable.width, sizeValue.toInt()) {
            contentPlaceable.place(0, 0)
        }
    }
}