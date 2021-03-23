package com.timmerl.mementoguesser.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementViewModel
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@ExperimentalFoundationApi
@Composable
fun MementoManagementsScreen(
    lifecycleOwner: LifecycleOwner,
    viewModel: MementoManagementViewModel
) {
    MaterialTheme(colors = lightTheme) {
        MementoManagementBaseScreen(
            mementos = viewModel.questionList.observeAsState(emptyList()),
            onItemClicked = {
                viewModel.toggleIsPlayable(it)
            })
    }
}

@ExperimentalFoundationApi
@Composable
fun MementoManagementBaseScreen(
    mementos: State<List<QuestionUiModel>>,
    onItemClicked: (QuestionUiModel) -> Unit
) {
    Scaffold(
//        topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp) {} }
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            MementoListView(mementos = mementos.value) {
                onItemClicked(it)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MementoListView(
    mementos: List<QuestionUiModel>,
    onItemClicked: (QuestionUiModel) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(1),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(mementos.size) { idx ->
            if (mementos.isNotEmpty() && idx in mementos.indices)
                MementoCard(
                    memento = mementos[idx],
                    onClicked = { onItemClicked(mementos[idx]) })
            else MementoCard(
                QuestionUiModel(
                    mementoId = 150L,
                    answerId = 250L,
                    question = "There is no Memento",
                    answer = "",
                    isPlayable = true,
                    showMenu = false
                )
            ) { /* No-Op */ }
        }
    }
}

@ExperimentalFoundationApi
@Preview
@Composable
fun MementoListPreview() {
    MaterialTheme(colors = lightTheme) {
        MementoListView(mementos = values) {}
    }
}

val values = listOf(
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


