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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timmerl.mementoguesser.presentation.lightTheme
import com.timmerl.mementoguesser.presentation.mementomanagement.MementoManagementViewModel
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel

/**
 * Created by Timmerman_Lyderic on 22/03/2021.
 */

@ExperimentalFoundationApi
@Composable
fun MementoMamanagementScreen(viewModel: MementoManagementViewModel) {
    val mementos = viewModel.questionList.observeAsState(emptyList())
    MaterialTheme(colors = lightTheme) {
        Scaffold(
//        topBar = { AppBar(title = "Rick and Morty", icon = Icons.Default.ExitToApp) {} }
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    cells = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(mementos.value.size) { idx ->
                        if (mementos.value.isNotEmpty() && idx in mementos.value.indices)
                            MementoCard(mementos.value[idx], onClicked = { /*TODO*/ })
                        else MementoCard(
                            QuestionUiModel(
                                mementoId = 150L,
                                answerId = 250L,
                                question = "There is no Memento",
                                answer = "",
                                isPlayable = true,
                                showMenu = false
                            )
                        ) { /*TODO*/ }
                    }
                }

            }
        }
    }
}