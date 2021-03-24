package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import com.timmerl.mementoguesser.presentation.nonPlayableQuestionTheme

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@Composable
fun MementoCard(memento: QuestionUiModel, onClicked: () -> Unit) =
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable(onClick = onClicked)
            .padding(8.dp),
        backgroundColor = if (memento.isPlayable) MaterialTheme.colors.surface else nonPlayableQuestionTheme.surface,
        contentColor = if (memento.isPlayable) MaterialTheme.colors.onSurface else nonPlayableQuestionTheme.onSurface,
        shape = MaterialTheme.shapes.large
    ) {
        MementoContent(memento.question, memento.answer)
    }

@Preview
@Composable
fun MementoCardPreview(@PreviewParameter(UiModelProvider::class) memento: QuestionUiModel) {
    MementoCard(memento = memento) {}
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