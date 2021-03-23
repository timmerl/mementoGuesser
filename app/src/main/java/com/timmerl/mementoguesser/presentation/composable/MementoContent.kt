package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@Composable
fun MementoContent(question: String, answer: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
    ) {
        Text(
            text = question,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
        )
        Text(
            text = answer,
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun MementoContentPreview(@PreviewParameter(MementoContentProvider::class) memento: MementoContentModel) {
    MementoContent(
        question = memento.question,
        answer = memento.answer
    )
}

class MementoContentProvider : PreviewParameterProvider<MementoContentModel> {
    override val values: Sequence<MementoContentModel> = sequenceOf(
        MementoContentModel(
            question = "question playable",
            answer = "answer"
        ),
        MementoContentModel(
            question = "question non playable",
            answer = "answer"
        )
    )
}

data class MementoContentModel(val question: String, val answer: String)