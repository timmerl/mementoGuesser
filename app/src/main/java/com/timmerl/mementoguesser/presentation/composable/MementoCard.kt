package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    MaterialTheme(colors = lightTheme) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(6.dp)
                .clickable(onClick = onClicked),
            elevation = 8.dp,
            backgroundColor = Color.White
        ) {
            MementoContent(memento, Alignment.End)
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//            CharacterImg(character.image, 72.dp)
//            MementoContent(memento, Alignment.CenterHorizontally)
//            }
        }
    }
}

@Preview
@Composable
fun MementoCardPreview(@PreviewParameter(UiModelProvider::class) memento: QuestionUiModel) {
    MementoCard(memento = memento) {}
}