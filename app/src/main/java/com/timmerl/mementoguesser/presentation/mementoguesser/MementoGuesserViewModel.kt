package com.timmerl.mementoguesser.presentation.mementoguesser

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
        viewModelScope.launch {
            continueGame(uiModel)
        }
    }

    fun onQuestionCardClicked() = uiModel.value?.let { uiModel ->
        viewModelScope.launch {
            continueGame(uiModel)
        }
    }

    fun onAnswerCardClicked() = uiModel.value?.let { uiModel ->
        viewModelScope.launch {
            continueGame(uiModel)
        }
    }

    fun toggleSorting() {
        toggleSortMode()
        initGame()
    }

    fun toggleQA() {
        toggleQaMode()
        initGame()
    }

    private fun initGame() {
        mementos = emptyList()
        onWelcomeCardClicked()
    }

    private suspend fun continueGame(uiModel: MementoGuesserUiModel) {
        if (mementos.isEmpty())
            mementos = adapter.getMementos(sortMode, showNonPlayable = false)
        postNextCard(uiModel)
    }


    private fun postNextCard(uiModel: MementoGuesserUiModel) {
        when (uiModel.cardType) {
            is CardType.Welcome -> postQuestionCard(uiModel)
            is CardType.Answer -> {
                increaseIdx()
                postQuestionCard(uiModel)
            }
            is CardType.Question -> postAnswerCard(uiModel)
        }
    }

    private fun increaseIdx() {
        currentIdx++
        if (currentIdx >= mementos.size)
            currentIdx = DEFAULT_IDX
    }

    private fun postQuestionCard(uiModel: MementoGuesserUiModel) {
        val memento = mementos[currentIdx]
        mutableUiModel.postValue(
            uiModel.copy(
                cardType = CardType.Question(
                    question = when (qaMode) {
                        QaMode.ImageFirst -> memento.image.name
                        QaMode.MemoryFirst -> memento.memory
                    }
                ),
                count = getCountText(),
            )
        )
    }

    private fun postAnswerCard(uiModel: MementoGuesserUiModel) {
        val memento = mementos[currentIdx]
        mutableUiModel.postValue(
            uiModel.copy(
                cardType = CardType.Answer(
                    answer = when (qaMode) {
                        QaMode.ImageFirst -> memento.memory
                        QaMode.MemoryFirst -> memento.image.name
                    }
                ),
            )
        )
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
        private val DEFAULT_SORT = ORDINAL
        private val DEFAULT_QA_MODE = QaMode.ImageFirst
        private const val DEFAULT_IDX = 0

        val defaultUiModel = MementoGuesserUiModel(
            cardType = CardType.Welcome,
            count = "",
            sortButtonText = getSortButtonText(DEFAULT_SORT),
            switchQAButtonText = getSwitchQAButtonText(DEFAULT_QA_MODE)
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
}

interface IQuestion {
    val question: String
}

interface IAnswer {
    val answer: String
}

sealed class CardType {
    object Welcome : CardType()

    data class Question(
        override val question: String
    ) : CardType(), IQuestion

    data class Answer(
        override val answer: String
    ) : CardType(), IAnswer
}

data class MementoGuesserUiModel(
    val cardType: CardType,
    val count: String = "",
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
