package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.RANDOM
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserViewModel(
    private val adapter: MementoAdapter
) : ViewModel() {


    private var sortMode = ORDINAL
    private var qaMode: QaMode = QaMode.ImageFirst
    private var gameState: GameState = GameState.Question

    private var mementos: List<Memento> = emptyList()

    private var mementoCount = -1
    private val welcomeUiModel = MementoGuesserUiModel(
        question = "Welcome",
        answer = "To this new Game",
        count = "",
        sortButtonText = 0,
        switchQAButtonText = 0
    )

    private val mementoMutable = MutableLiveData(welcomeUiModel)
    val memento: LiveData<MementoGuesserUiModel> = mementoMutable

    fun startGame() {
        viewModelScope.launch {
            retrieveMementos()
            gameState = gameState.getFirst()
            mementoCount = DEFAULT_COUNT
            continueGame()
        }
    }

    fun continueGame() {
        gameState = gameState.getNext()
        increaseMementoCount()
        setCurrentMemento()
    }

    fun toggleSorting() {
        toggleSortMode()
        startGame()
    }

    fun toggleQA() {
        toggleQaMode()
        startGame()
    }

    private suspend fun retrieveMementos() {
        mementos = adapter.getMementos(sortMode)
    }

    private fun increaseMementoCount() {
        if (gameState is GameState.Question)
            mementoCount++
        if (mementoCount >= mementos.size)
            mementoCount = STARTING_COUNT
    }


    private fun setCurrentMemento() {
        mementoMutable.postValue(
            if (mementoCount == DEFAULT_COUNT)
                welcomeUiModel
            else mementos[mementoCount].toUiModel()
        )
    }

    private fun Memento.toUiModel() =
        MementoGuesserUiModel(
            question = getQuestion(),
            answer = getAnswer(),
            count = getCountText(),
            sortButtonText = getSortButtonText(),
            switchQAButtonText = getSwitchQAButtonText()
        )

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

    private fun toggleQaMode() {
        qaMode = qaMode.getNext()
    }

    private fun getCountText() = "Memento No $mementoCount"

    interface IState<T> {
        fun getNext(): T
        fun getFirst(): T
    }

    sealed class GameState : IState<GameState> {
        override fun getFirst(): GameState = None

        object None : GameState(), IState<GameState> {
            override fun getNext(): GameState = Question
        }

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

    companion object {
        private const val DEFAULT_COUNT = -1
        private const val STARTING_COUNT = 0
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
