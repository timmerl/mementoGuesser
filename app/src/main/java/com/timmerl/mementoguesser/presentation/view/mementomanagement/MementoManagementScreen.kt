package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.timmerl.mementoguesser.presentation.common.MgCard
import com.timmerl.mementoguesser.presentation.common.MgSurface
import com.timmerl.mementoguesser.presentation.model.MementoCardUiModel
import com.timmerl.mementoguesser.presentation.theme.MementoGuesserTheme
import com.timmerl.mementoguesser.presentation.theme.MgTheme

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@ExperimentalFoundationApi
@Composable
fun MementoManagementsScreen(
    viewModel: MementoManagementViewModel
) {
    MementoManagementsBaseScreen(
        mementos = viewModel.questionList.observeAsState(emptyList()),
        toggleIsPlayable = viewModel::toggleIsPlayable,
        remove = viewModel::remove
    )
}

@ExperimentalFoundationApi
@Composable
fun MementoManagementsBaseScreen(
    mementos: State<List<MementoCardUiModel>>,
    toggleIsPlayable: (MementoCardUiModel) -> Unit,
    remove: (MementoCardUiModel) -> Unit
) {
    MgTheme {
        MgSurface(modifier = Modifier.fillMaxSize()) {
            MementoListView(
                mementos = mementos,
                onItemClicked = toggleIsPlayable,
                onRemove = remove,
                onEmptyAction = {}
            )
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun MementoListView(
    mementos: State<List<MementoCardUiModel>>,
    onItemClicked: (MementoCardUiModel) -> Unit,
    onRemove: (MementoCardUiModel) -> Unit,
    onEmptyAction: () -> Unit
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
            } else EmptyMementoList(onEmptyAction)
        }
    }
}

@Composable
fun EmptyMementoList(onClicked: () -> Unit) {
    MgCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClicked)
            .wrapContentHeight()
            .padding(8.dp),
        backgroundColor = MementoGuesserTheme.colors.error,
        contentColor = MementoGuesserTheme.colors.onError,
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
fun MementoMannagementScreenPreview(
    @PreviewParameter(
        MementoListProvider::class
    ) list: List<MementoCardUiModel>
) {
    MementoManagementsBaseScreen(
        mementos = mutableStateOf(list),
        toggleIsPlayable = {},
        remove = {}
    )
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MementoListViewPreview(@PreviewParameter(MementoListProvider::class) list: List<MementoCardUiModel>) {
    MgTheme {
        MementoListView(
            mementos = mutableStateOf(list),
            onItemClicked = {},
            onRemove = {},
            onEmptyAction = {},
        )
    }
}

class MementoListProvider : PreviewParameterProvider<List<MementoCardUiModel>> {
    override val values: Sequence<List<MementoCardUiModel>> = sequenceOf(
        listOf(
            MementoCardUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            MementoCardUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question non playable",
                image = "answer",
                isPlayable = false
            ),
            MementoCardUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            MementoCardUiModel(
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
