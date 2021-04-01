package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.common.horizontalGradientBackground
import com.timmerl.mementoguesser.presentation.theme.MgTheme

class MementoCardNewState(
    questionBackgroundColor: Color,
    answerBackgroundColor: Color,
    contentColor: Color,
) {
    val questionBackgroundColor: Color by mutableStateOf(value = questionBackgroundColor)
    val answerBackgroundColor: Color by mutableStateOf(value = answerBackgroundColor)
    val contentColor: Color by mutableStateOf(value = contentColor)
}


@Composable
fun MementoCardNew(
    memory: String,
    image: String,
    onClicked: () -> Unit = {},
    state: MementoCardNewState,
) = Row(
    modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight()
        .clickable(onClick = onClicked)
        .defaultMinSize(minWidth = 150.dp)
        .horizontalGradientBackground(
            listOf(
                state.questionBackgroundColor,
                state.answerBackgroundColor
            )
        )
        .padding(6.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    MemoryCard(
        message = memory, contentColor = state.contentColor,
    )
    ImageCard(
        message = image, contentColor = state.contentColor
    )
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
            .wrapContentWidth(),
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
            .wrapContentWidth(),
        textAlign = TextAlign.End
    )
}


@Preview
@Composable
fun PlayableMementoCardNewPreview() {
    MgTheme() {
        MementoCardNew(
            memory = "12",
            image = "Image un peu longue",
            state = MementoCardNewState(
                questionBackgroundColor = MgTheme.colors.questionBackground(1),
                answerBackgroundColor = MgTheme.colors.answerBackground(1),
                contentColor = MgTheme.colors.question(1)
            )
        )
    }
}


@Preview
@Composable
fun NonPlayableMementoCardNewPreview() {
    MgTheme() {
        MementoCardNew(
            memory = "12",
            image = "image",
            state = MementoCardNewState(
                questionBackgroundColor = MgTheme.colors.surfaceNotAvailable,
                answerBackgroundColor = MgTheme.colors.surfaceNotAvailable,
                contentColor = MgTheme.colors.onSurfaceNotAvailable
            ),
            onClicked = {},
        )
    }
}