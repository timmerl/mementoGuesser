package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.view.mementoguesser.WelcomeScreen

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
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        MementoListView(
            mementos = viewModel.questionList.observeAsState(emptyList()),
            onItemClicked = viewModel::toggleIsPlayable,
            onRemove = viewModel::remove,
            onEmptyAction = onEmptyAction,
            onEditAction = onEditAction,
            onItemCollapsed = viewModel::onItemCollapsed,
            onItemExpanded = viewModel::onItemExpanded,
            revealedCards = viewModel.revealedCardIdsList.collectAsState()
        )
    }
}

/*
    fixme when anitem is deleted, it breaks the list items sliding
        actual : the item right below lose its sliding behavior
                  and items below are controled by the item on bottom
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
    onEmptyAction: () -> Unit,
    onItemExpanded: (imageId: Long) -> Unit,
    onItemCollapsed: (imageId: Long) -> Unit,
    revealedCards: State<List<Long>>
) {
    val list = mementos.value
    if (list.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxHeight()
                .width(200.dp)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list.size) { idx ->
                if (list.isNotEmpty() && idx in list.indices) {
                    val memento = list[idx]
                    MementoSliderItem(
                        memory = memento.memory,
                        image = memento.image,
                        onSliderClicked = { onItemClicked(memento) },
                        onRemoveClicked = { onRemove(memento) },
                        onEditClicked = {
                            onEditAction(
                                memento.mementoId,
                                memento.imageId
                            )
                        },
                        state = itemDefaultColor(idx = idx, isPlayable = memento.isPlayable),
                        isRevealed = mutableStateOf(revealedCards.value.contains(memento.imageId)),
                        onExpanded = { onItemExpanded(memento.imageId) },
                        onCollapsed = { onItemCollapsed(memento.imageId) },
                    )
                }
            }
        }
    } else WelcomeScreen(onEmptyAction)
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
            onEditAction = { _, _ -> },
            onItemExpanded = {},
            onItemCollapsed = {},
            revealedCards = mutableStateOf(emptyList()),
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
