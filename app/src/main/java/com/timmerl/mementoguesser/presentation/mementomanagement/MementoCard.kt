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
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme

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
            backgroundColor = MementoGuesserTheme.colors.surfaceNotAvailable,
            contentColor = MementoGuesserTheme.colors.OnSurfaceNotAvailable
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
        backgroundColor = MementoGuesserTheme.colors.surface,
        contentColor = MementoGuesserTheme.colors.onSurface
    ),
    onRemove: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp)
            .clickable(onClick = onClicked),
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
                .padding(bottom = 8.dp, start = 8.dp),
        ) {
            Text(
                text = memory,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = image,
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentWidth(align = Alignment.End)
                        .padding(top = 8.dp, end = 8.dp)

                )
                Image(
                    painter = painterResource(android.R.drawable.ic_delete),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = onRemove
                        )
                        .size(8.dp)
                        .wrapContentHeight()
                        .wrapContentWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun PlayableMementoCardPreview() {
    PlayableMementoCard(
        memory = "Playable memento",
        image = "Image"
    )
}


@Preview
@Composable
fun NonPlayableMementoCardPreview() {
    NonPlayableMementoCard(memory = "Non playable memento", image = "image")
}