package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
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
import com.timmerl.mementoguesser.presentation.theme.MgTheme

class MementoCardNewState(
    questionBackgroundColor: Color,
    answerBackgroundColor: Color,
    questionContentColor: Color,
    answerContentColor: Color
) {
    val questionBackgroundColor: Color by mutableStateOf(value = questionBackgroundColor)
    val answerBackgroundColor: Color by mutableStateOf(value = answerBackgroundColor)
    val questionContentColor: Color by mutableStateOf(value = questionContentColor)
    val answerContentColor: Color by mutableStateOf(value = answerContentColor)
}


@Composable
fun MementoCardNew(
    memory: String,
    image: String,
    onClicked: () -> Unit = {},
    state: MementoCardNewState
) = Row(
    modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight()
        .clickable(onClick = onClicked),
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
) {
    HalfMementoCard(
        message = memory,
        backgroundColor = state.questionBackgroundColor,
        contentColor = state.questionContentColor
    )
    HalfMementoCard(
        message = image,
        backgroundColor = state.answerBackgroundColor,
        contentColor = state.answerContentColor
    )
}

@Composable
fun HalfMementoCard(
    message: String,
    backgroundColor: Color,
    contentColor: Color,
) {
    Surface(
        color = backgroundColor,
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = message,
                color = contentColor,
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
fun PlayableMementoCardNewPreview() {
    MementoCardNew(
        memory = "Playable memento Avec plaienf gijiovh iuhg iuhg fkiudr gfk gkudg uk fsv",
        image = "Image",
        state = MementoCardNewState(
            questionBackgroundColor = MgTheme.colors.questionBackground(0),
            answerBackgroundColor = MgTheme.colors.answerBackground(0),
            questionContentColor = MgTheme.colors.question(0),
            answerContentColor = MgTheme.colors.answer(0)
        )

    )
}


@Preview
@Composable
fun NonPlayableMementoCardNewPreview() {
    MementoCardNew(
        memory = "Non playable mementoPlayable memento Avec plaienf gijiovh iuhg iuhg fkiudr gfk gkudg uk fsv",
        image = "image",
        state = MementoCardNewState(
            questionBackgroundColor = MgTheme.colors.surfaceNotAvailable,
            answerBackgroundColor = MgTheme.colors.surfaceNotAvailable,
            questionContentColor = MgTheme.colors.onSurfaceNotAvailable,
            answerContentColor = MgTheme.colors.onSurfaceNotAvailable
        )
    )
}