package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.RANDOM
import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserViewModel(
    private val adapter: MementoAdapter
) : ViewModel() {


    private var sortMode = ORDINAL
    private var qaMode: QaMode = QaMode.ImageFirst
    private var gameState: GameState = GameState.Question

    private fun mementos() = adapter.getMementoFlow(sortMode, false)
    private var mementoCount = 0
    private var currentMemento = mementos().map {
        if (it.isEmpty()) {
            Memento(
                id = -1,
                memory = "Welcome",
                image = Image(
                    name = "To this new game",
                    isPlayable = true,
                    id = -1,
                    mementoId = -1
                )
            )
        } else it[mementoCount]
    }

    val memento = currentMemento
        .toUiModel()
        .asLiveData(viewModelScope.coroutineContext)

    private fun Flow<Memento>.toUiModel() = map {
        MementoGuesserUiModel(
            question = it.getQuestion(),
            answer = it.getAnswer(),
            count = getCountText(),
            sortButtonText = getSortButtonText(),
            switchQAButtonText = getSwitchQAButtonText()
        )
    }

    private fun Memento.getQuestion(): String = when (qaMode) {
        is QaMode.MemoryFirst -> memory
        is QaMode.ImageFirst -> image.name
    }

    private fun Memento.getAnswer(): String = when {
        gameState is GameState.Question -> ""
        qaMode is QaMode.ImageFirst -> memory
        qaMode is QaMode.MemoryFirst -> image.name
        else -> "this message should never be displayed"
    }

    fun startGame() {
        gameState = gameState.getFirst()
        mementoCount = 0
        continueGame()
    }

    fun continueGame() {
        if (gameState is GameState.Answer)
            mementoCount++
        gameState = gameState.getNext()
    }

    fun toggleSorting() {
        toggleSortMode()
        startGame()
    }

    fun toggleQA() {
        qaMode = qaMode.getNext()
        startGame()
    }


    private fun getSortButtonText() = if (sortMode == RANDOM)
        R.string.sort_by_rand
    else R.string.sort_by_order

    private fun getSwitchQAButtonText() = if (qaMode is QaMode.ImageFirst)
        R.string.image_first
    else R.string.memory_first

    private fun toggleSortMode() {
        sortMode = if (sortMode == RANDOM)
            ORDINAL
        else RANDOM
    }

    private fun getCountText() = "Memento No $mementoCount"

    interface IState<T> {
        fun getNext(): T
        fun getFirst(): T
    }

    sealed class GameState : IState<GameState> {
        override fun getFirst(): GameState = Question

        object Question : GameState(), IState<GameState> {
            override fun getNext(): GameState = Answer
        }

        object Answer : GameState(), IState<GameState> {
            override fun getNext(): GameState = Question
        }
    }

    sealed class QaMode : IState<QaMode> {
        override fun getFirst(): QaMode = ImageFirst

        object ImageFirst : QaMode(), IState<QaMode> {
            override fun getNext(): QaMode = MemoryFirst
        }

        object MemoryFirst : QaMode(), IState<QaMode> {
            override fun getNext(): QaMode = ImageFirst
        }
    }
}

data class MementoGuesserUiModel(
    val question: String,
    val answer: String? = null,
    val count: String = "",
    @StringRes
    val sortButtonText: Int,
    @StringRes
    val switchQAButtonText: Int
)
