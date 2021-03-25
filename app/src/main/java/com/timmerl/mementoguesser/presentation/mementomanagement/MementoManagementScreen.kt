package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@ExperimentalFoundationApi
@Composable
fun MementoManagementsScreen(
    viewModel: MementoManagementViewModel
) {
    MaterialTheme(colors = lightTheme) {
        Scaffold(
//        topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp) {} }
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                MementoListView(
                    mementos = viewModel.questionList.observeAsState(emptyList()),
                    onItemClicked = viewModel::toggleIsPlayable,
                    onRemove = viewModel::remove
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MementoListView(
    mementos: State<List<QuestionUiModel>>,
    onItemClicked: (QuestionUiModel) -> Unit = {},
    onRemove: (QuestionUiModel) -> Unit = {}
) {
    val list = mementos.value

    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(list.size) { idx ->
            if (list.isNotEmpty() && idx in list.indices) {
                val memento = list[idx]
                if (memento.isPlayable)
                    PlayableMementoCard(
                        memory = memento.memory,
                        image = memento.image,
                        onClicked = { onItemClicked(memento) },
                        onRemove = { onRemove(memento) }
                    )
                else NonPlayableMementoCard(
                    memory = memento.memory,
                    image = memento.image,
                    onClicked = { onItemClicked(list[idx]) },
                    onRemove = { onRemove(memento) }
                )
            } else EmptyMementoList()
        }
    }
}

@Composable
fun EmptyMementoList() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        backgroundColor = lightTheme.error,
        contentColor = lightTheme.onError,
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
                text = "Aucun momento enregistré",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MementoListViewPreview(@PreviewParameter(MementoListProvider::class) list: List<QuestionUiModel>) {
    MaterialTheme(colors = lightTheme) {
        MementoListView(mementos = mutableStateOf(list)) {}
    }
}

class MementoListProvider : PreviewParameterProvider<List<QuestionUiModel>> {
    override val values: Sequence<List<QuestionUiModel>> = sequenceOf(
        listOf(
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question non playable",
                image = "answer",
                isPlayable = false
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question non playable",
                image = "answer",
                isPlayable = false
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            QuestionUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            )
        ),
        emptyList()
    )
}
