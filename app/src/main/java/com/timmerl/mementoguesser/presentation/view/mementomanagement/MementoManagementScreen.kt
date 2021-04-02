package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.timmerl.mementoguesser.presentation.theme.MgTheme

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MementoManagementsScreen(
    viewModel: MementoManagementViewModel,
    onEmptyAction: () -> Unit,
    onEditAction: (mementoId: Long, imageId: Long) -> Unit
) {
    MementoListView(
        mementos = viewModel.questionList.observeAsState(emptyList()),
        onItemClicked = viewModel::toggleIsPlayable,
        onRemove = viewModel::remove,
        onEmptyAction = onEmptyAction,
        onEditAction = onEditAction
    )
}

/*
    TODO
      when two item are opened. if I delete the top one, the second one will stay opened.
      but value has change. It tooks the value of above ... cuz everything went up
 */

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MementoListView(
    mementos: State<List<MementoListItemUiModel>>,
    onItemClicked: (MementoListItemUiModel) -> Unit,
    onEditAction: (mementoId: Long, imageId: Long) -> Unit,
    onRemove: (MementoListItemUiModel) -> Unit,
    onEmptyAction: () -> Unit
) {
    val list = mementos.value

    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list.size) { idx ->
            if (list.isNotEmpty() && idx in list.indices) {
                val memento = list[idx]
                MementoListItem(
                    memory = memento.memory,
                    image = memento.image,
                    onClick = { onItemClicked(memento) },
                    onRemoveClicked = {
                        onRemove(memento)
                    },
                    onEditClicked = {
                        onEditAction(
                            memento.mementoId,
                            memento.imageId
                        )
                    },
                    state = if (memento.isPlayable) {
                        MementoListItemColorState(
                            questionBackgroundColor = MgTheme.colors.questionBackground(idx),
                            answerBackgroundColor = MgTheme.colors.answerBackground(idx),
                            contentColor = MgTheme.colors.questionContent(idx),
                        )
                    } else {
                        MementoListItemColorState(
                            questionBackgroundColor = MgTheme.colors.error,
                            answerBackgroundColor = MgTheme.colors.error,
                            contentColor = contentColorFor(backgroundColor = MgTheme.colors.error),
                        )
                    }
                )
            } else EmptyMementoList(onEmptyAction)
        }
    }
}

@Composable
fun EmptyMementoList(onClicked: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClicked)
            .wrapContentHeight()
            .padding(8.dp),
        backgroundColor = MgTheme.colors.error,
        contentColor = MgTheme.colors.onError,
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

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun MementoManagementScreenPreview(
    @PreviewParameter(
        MementoListProvider::class
    ) list: List<MementoListItemUiModel>
) {
    MgTheme {
        MementoListView(
            mementos = mutableStateOf(list),
            onItemClicked = {},
            onRemove = {},
            onEmptyAction = {},
            onEditAction = { _, _ -> }
        )
    }
}

class MementoListProvider : PreviewParameterProvider<List<MementoListItemUiModel>> {
    override val values: Sequence<List<MementoListItemUiModel>> = sequenceOf(
        listOf(
            MementoListItemUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            MementoListItemUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question non playable",
                image = "answer",
                isPlayable = false
            ),
            MementoListItemUiModel(
                mementoId = 150L,
                imageId = 150L,
                memory = "question playable",
                image = "answer",
                isPlayable = true
            ),
            MementoListItemUiModel(
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
