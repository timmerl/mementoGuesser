package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.nonPlayableQuestionTheme

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
    MaterialTheme(memento.colors) {
        MementoContent(
            question = memento.question,
            answer = memento.answer
        )
    }
}

class MementoContentProvider : PreviewParameterProvider<MementoContentModel> {
    override val values: Sequence<MementoContentModel> = sequenceOf(
        MementoContentModel(
            question = "question playable",
            answer = "answer",
            colors = lightTheme
        ),
        MementoContentModel(
            question = "question non playable",
            answer = "answer",
            colors = nonPlayableQuestionTheme
        )
    )
}

data class MementoContentModel(val question: String, val answer: String, val colors: Colors)