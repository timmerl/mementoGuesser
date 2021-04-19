package com.timmerl.mementoguesser.presentation.view.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

const val ANIMATION_DURATION = 250

@Composable
fun DraggableItem(
    cardHeight: Dp,
    isRevealed: State<Boolean>,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    content: @Composable () -> Unit
) {
    val offsetX = remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed.value).apply {
            targetState = !isRevealed.value
        }
    }
    val transition = updateTransition(transitionState, label = "draggableItemTransition")
    val offsetTransition by transition.animateFloat(
        { tween(durationMillis = ANIMATION_DURATION) },
        label = "draggableItemOffsetTransition"
    ) {
        if (isRevealed.value) cardOffset - offsetX.value else -offsetX.value
    }
    val cardElevation by transition.animateDp(
        { tween(durationMillis = ANIMATION_DURATION) },
        label = "draggableItemTransition"
    ) {
        if (isRevealed.value) 40.dp else 2.dp
    }

    Surface(
        modifier = Modifier
            .height(cardHeight)
            .offset { IntOffset((offsetX.value + offsetTransition).roundToInt(), 0) }
            .pointerInput("key") {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX.value, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        return@detectHorizontalDragGestures
                    }
                    change.consumePositionChange()
                    offsetX.value = newValue.x
                }
            },
        elevation = cardElevation,
        content = content
    )
}