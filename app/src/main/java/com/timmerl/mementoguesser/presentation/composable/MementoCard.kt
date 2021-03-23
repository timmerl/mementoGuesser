package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@Composable
fun MementoCard(memento: QuestionUiModel, onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(6.dp)
            .clickable(onClick = onClicked),
        elevation = 8.dp,
    ) {
        MementoContent(memento, Alignment.End)
    }
}

@Preview
@Composable
fun MementoCardPreview(@PreviewParameter(UiModelProvider::class) memento: QuestionUiModel) {
    MaterialTheme(colors = lightTheme) {
        MementoCard(memento = memento) {}
    }
}