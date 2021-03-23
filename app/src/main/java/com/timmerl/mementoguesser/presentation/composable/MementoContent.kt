package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@Composable
fun MementoContent(memento: QuestionUiModel, alignment: Alignment.Horizontal) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = memento.question,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
        )
        Text(
            text = memento.answer,
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun MementoContentPreview(@PreviewParameter(UiModelProvider::class) memento: QuestionUiModel) {
    MementoContent(
        memento = memento,
        alignment = Alignment.CenterHorizontally
    )
}

class UiModelProvider : PreviewParameterProvider<QuestionUiModel> {
    override val values: Sequence<QuestionUiModel> = sequenceOf(
        QuestionUiModel(
            mementoId = 150L,
            answerId = 250L,
            question = "question playable",
            answer = "answer",
            isPlayable = true,
            showMenu = false
        ),
        QuestionUiModel(
            mementoId = 150L,
            answerId = 250L,
            question = "question non playable",
            answer = "answer",
            isPlayable = false,
            showMenu = false
        )
    )
}