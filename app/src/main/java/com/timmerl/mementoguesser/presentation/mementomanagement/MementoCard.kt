package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.nonPlayableQuestionTheme

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */


@Composable
fun PlayableMementoCard(
    memory: String,
    image: String,
    onClicked: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    MementoCard(
        memory = memory,
        image = image,
        onClicked = onClicked,
        onRemove = onRemove
    )
}

@Composable
fun NonPlayableMementoCard(
    memory: String,
    image: String,
    onClicked: () -> Unit = {},
    onRemove: () -> Unit = {}
) {
    MementoCard(
        memory = memory,
        image = image,
        onClicked = onClicked,
        onRemove = onRemove,
        state = MementoCardState(
            backgroundColor = nonPlayableQuestionTheme.surface,
            contentColor = nonPlayableQuestionTheme.onSurface
        )
    )
}

class MementoCardState(backgroundColor: Color, contentColor: Color) {
    val backgroundColor: Color by mutableStateOf(value = backgroundColor)
    val contentColor: Color by mutableStateOf(value = contentColor)
}

@Composable
fun MementoCard(
    memory: String,
    image: String,
    onClicked: () -> Unit = {},
    state: MementoCardState = MementoCardState(
        backgroundColor = lightTheme.surface,
        contentColor = lightTheme.onSurface
    ),
    onRemove: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClicked)
            .padding(8.dp),
        backgroundColor = state.backgroundColor,
        contentColor = state.contentColor,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
        ) {
            Text(
                text = memory,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
            )
            Text(
                text = image,
                style = MaterialTheme.typography.h6
            )

            Image(
                painter = painterResource(android.R.drawable.ic_delete),
                contentDescription = null,
                modifier = Modifier.clickable(
                    enabled = true,
                    onClickLabel = "Clickable image",
                    onClick = onRemove
                )
            )
        }
    }
}

@Preview
@Composable
fun PlayableMementoCardPreview() {
    PlayableMementoCard(memory = "Playable memento", image = "image")
}


@Preview
@Composable
fun NonPlayableMementoCardPreview() {
    NonPlayableMementoCard(memory = "Non playable memento", image = "image")
}