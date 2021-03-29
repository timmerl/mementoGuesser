package com.timmerl.mementoguesser.presentation.view.mementoguesser

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.RANDOM
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserViewModel(
    private val adapter: MementoAdapter
) : ViewModel() {

    private var sortMode = DEFAULT_SORT
    private var qaMode: QaMode = DEFAULT_QA_MODE
    private var currentIdx = DEFAULT_IDX
    private var mementos: List<Memento> = emptyList()

    private val mutableUiModel = MutableLiveData(defaultUiModel)
    val uiModel: LiveData<MementoGuesserUiModel> = mutableUiModel

    fun onWelcomeCardClicked() = uiModel.value?.let { uiModel ->
        viewModelScope.launch(Dispatchers.IO) {
            mementos = adapter.getMementos(sortMode, showNonPlayable = false)
            if (mementos.isEmpty()) {
//                navigateToAddMemento()
            } else showGuess(uiModel)
        }
    }

    fun onShowAnswer() = uiModel.value?.let { uiModel ->
        viewModelScope.launch(Dispatchers.IO) {
            openCurtain(uiModel)
        }
    }

    fun onShowNextMemento() = uiModel.value?.let { uiModel ->
        viewModelScope.launch(Dispatchers.IO) {
            delay(300) // TODO REMOVE (used to hide next answer while animate ...)
            nextMemento()
            showGuess(uiModel)
        }
    }

    fun onSortButtonCLick() {
        toggleSortMode()
        initGame()
    }

    fun onQuaModeButtonClick() {
        toggleQaMode()
        initGame()
    }

    private fun initGame() {
        mementos = emptyList()
        onWelcomeCardClicked()
    }

    private suspend fun nextMemento() {
        currentIdx++
        if (currentIdx >= mementos.size) {
            mementos = adapter.getMementos(sortMode, showNonPlayable = false)
            currentIdx = DEFAULT_IDX
        }
    }

    private fun showGuess(uiModel: MementoGuesserUiModel) {
        val memento = mementos[currentIdx]
        mutableUiModel.postValue(
            uiModel.copy(
                cardType = when (qaMode) {
                    QaMode.ImageFirst ->
                        CardType.Guess(
                            question = memento.image.name,
                            answer = memento.memory,
                            curtainIsOpen = false
                        )
                    QaMode.MemoryFirst -> CardType.Guess(
                        question = memento.memory,
                        answer = memento.image.name,
                        curtainIsOpen = false
                    )
                },
                count = currentIdx,
                countMessage = getCountText(),
            )
        )
    }

    private fun openCurtain(uiModel: MementoGuesserUiModel) {
        if (uiModel.cardType is CardType.Guess) {
            mutableUiModel.postValue(
                uiModel.copy(
                    cardType = uiModel.cardType.copy(
                        curtainIsOpen = true
                    )
                )
            )
        }
    }

    private fun toggleSortMode() {
        sortMode = if (sortMode == RANDOM)
            ORDINAL
        else RANDOM
    }

    private fun toggleQaMode() {
        qaMode = qaMode.getNext()
    }

    private fun getCountText() = "Memento No ${currentIdx + 1}/${mementos.size}"


    companion object {
        private val DEFAULT_SORT = RANDOM
        private val DEFAULT_QA_MODE = QaMode.MemoryFirst
        private const val DEFAULT_IDX = 0

        val defaultUiModel = MementoGuesserUiModel(
            cardType = CardType.Welcome,
            countMessage = "",
            count = 0,
            sortButtonText = getSortButtonText(DEFAULT_SORT),
            switchQAButtonText = getSwitchQAButtonText(DEFAULT_QA_MODE),
        )

        @StringRes
        private fun getSortButtonText(sortMode: SortType): Int =
            if (sortMode == RANDOM)
                R.string.sort_by_order
            else R.string.sort_by_rand

        @StringRes
        private fun getSwitchQAButtonText(qaMode: QaMode) =
            if (qaMode is QaMode.ImageFirst)
                R.string.memory_first
            else R.string.image_first
    }

    sealed class MementoGuesserUiEvent {
        object OpenCurtain : MementoGuesserUiEvent()
    }
}

sealed class CardType {
    object Welcome : CardType()

    data class Guess(
        val question: String,
        val answer: String,
        val curtainIsOpen: Boolean
    ) : CardType()

}

data class MementoGuesserUiModel(
    val cardType: CardType,
    val count: Int,
    val countMessage: String = "",
    @StringRes
    val sortButtonText: Int,
    @StringRes
    val switchQAButtonText: Int
)

interface IState<DATA> {
    fun getNext(): DATA
    fun getFirst(): DATA
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
