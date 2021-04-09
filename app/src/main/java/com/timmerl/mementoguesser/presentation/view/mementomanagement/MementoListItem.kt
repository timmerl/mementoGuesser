package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.common.horizontalGradientBackground
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.common.DraggableItem
import com.timmerl.mementoguesser.presentation.view.common.DraggableItemActionRowActionRow

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

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MementoSliderItem(
    state: MementoListItemColorState,
    isRevealed: State<Boolean>,
    memory: String,
    image: String,
    onExpanded: () -> Unit,
    onCollapsed: () -> Unit,
    onSliderClicked: () -> Unit,
    onRemoveClicked: () -> Unit = {},
    onEditClicked: () -> Unit = {},
) {
    val contentHeight = 45.dp
    val imageSize = 35.dp
    val swipeDistancePx = with(LocalDensity.current) { contentHeight.toPx() * 2 }
    val exists = remember { mutableStateOf(true) }
    exists.value = true
//    AnimatedVisibility(
//        visible = exists.value,
//        exit = fadeOut()
//    ) {
    Box(Modifier.fillMaxWidth()) {
        DraggableItemActionRowActionRow(
            actionIconSize = imageSize,
            onDelete = { onRemoveClicked() },
            onEdit = onEditClicked
        )
        DraggableItem(
            cardHeight = contentHeight,
            isRevealed = isRevealed,
            cardOffset = swipeDistancePx,
            onExpand = onExpanded,
            onCollapse = onCollapsed
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable(
                        onClick = onSliderClicked,
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
    }
}
//}

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