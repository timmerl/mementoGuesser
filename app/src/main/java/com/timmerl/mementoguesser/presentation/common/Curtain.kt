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

@Composable
fun Curtain(
    isOpenedFromOutside: Boolean? = null,
    foldingDuration: Int = 250,
    onOpenEnds: () -> Unit,
    onCloseEnds: () -> Unit,
    mainCell: @Composable () -> Unit,
    foldCells: List<@Composable () -> Unit>
) {
    var isOpened by remember { mutableStateOf(false) }
    var isTransitionRunning by remember { mutableStateOf(false) }
    val foldScope = rememberCoroutineScope()

    fun toggleCurtain() {
        if (!isTransitionRunning) {
            isTransitionRunning = true
            isOpened = !isOpened
            val localIsOpen = isOpened

            foldScope.launch {
                delay(foldingDuration.toLong() * foldCells.size)
                isTransitionRunning = false
                if (localIsOpen)
                    onOpenEnds()
                else onCloseEnds()
            }
        }
    }

    if (isOpenedFromOutside != null) {
        isOpened = isOpenedFromOutside
    }

    Column(
        modifier = Modifier.curtainModifier(
            isOpenedFromOutside != null,
            onClick = { toggleCurtain() }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainCell(
//            isOpened = isOpened,
//            cellsQuantity = foldCells.size,
//            foldingDuration = foldingDuration,
            content = mainCell
        )
        FoldedCells(
            isOpened = isOpened,
            foldingDuration = foldingDuration,
            foldCells = foldCells
        )
    }
}

private fun Modifier.curtainModifier(
    externalControl: Boolean = false,
    onClick: () -> Unit
): Modifier {
    val modifier = wrapContentSize()
    return if (!externalControl) modifier.clickable { onClick() } else modifier
}

@Composable
private fun MainCell(
//    isOpened: Boolean,
//    cellsQuantity: Int,
//    foldingDuration: Int,
    content: @Composable () -> Unit
) {
//    val mainCellTransition = updateTransition(targetState = isOpened)

//    val mainCellAlpha by mainCellTransition.animateFloat(
//        transitionSpec = {
//            tween(
//                durationMillis = 100,
//                delayMillis = if (isOpened) 0 else foldingDuration * cellsQuantity
//            )
//        }
//    ) { state ->
//        when (state) {
//            false -> 1f
//            true -> 0f
//        }
//    }

    Box() {
        content()
    }
}

@Composable
private fun FoldedCells(
    isOpened: Boolean,
    foldingDuration: Int,
    foldCells: List<@Composable () -> Unit>
) {
    Column {
        foldCells.forEachIndexed { index, cell ->
            FoldedCell(
                isOpened = isOpened,
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
    isOpened: Boolean,
    cellsQuantity: Int,
    foldingDuration: Int,
    index: Int,
    content: @Composable () -> Unit
) {
    var cellMaxHeight by remember { mutableStateOf(0.dp) }
    val transition = updateTransition(targetState = isOpened)
    val foldingDelay =
        if (isOpened) foldingDuration * index else foldingDuration * (cellsQuantity - index)

    val rotationValue by transition.animateFloat(transitionSpec = {
        tween(
            durationMillis = foldingDuration,
            delayMillis = foldingDelay
        )
    }) { state ->
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
    }) { state ->
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
    }) { state ->
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