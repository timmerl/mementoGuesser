package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.mapper.toUiModel
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserViewModel(
    private val adapter: MementoInteractor
) : ViewModel() {


    private var sortMode = SortMode.ORDINAL
    private var qa = GameMode.IMAGE_FIRST
    private var state: State = State.Question
    private var currentQuestion = QuestionUiModel(
        mementoId = -1,
        answerId = -1,
        question = "",
        answer = "",
        isPlayable = false,
        showMenu = false
    )
    private var questionCount = -1

    private var banList = mutableListOf(currentQuestion)
    private val mutableRandomQuestion = MutableLiveData(
        GameUiModel(question = "waiting", sortButtonText = 0, switchQAButtonText = 0)
    )
    val randomQuestion: LiveData<GameUiModel> = mutableRandomQuestion


    fun startGame() {
        state = state.getFirst()
        questionCount = -1
        banList.clear()
        continueGame()
    }


    fun continueGame() {
        when (state) {
            State.Question -> getAnswer()
            State.Answer -> getQuestion()
        }
        state = state.getNext()
    }

    fun toggleSorting() {
        questionCount = -1
        toggleSortMode()
        banList.clear()
        state = state.getFirst()
        continueGame()
    }

    fun toggleQA() {

    }


    private fun getQuestion() =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.getMementos(ORDINAL)
                .toUiModel()
                .collect {
                    when (sortMode) {
                        SortMode.RANDOM -> it.getRandom()
                        SortMode.ORDINAL -> it.getNext()
                    }
                }
        }

    private fun getAnswer() {
        mutableRandomQuestion.postValue(
            randomQuestion.value?.copy(
                answer = currentQuestion.answer
            )
        )
    }

    private fun List<QuestionUiModel>.getRandom() {
        questionCount = size
        try {
            var newQuestion = random()
            if (isBanListFull(questionCount))
                banList.removeAt(0)

            while (banList.contains(newQuestion)) {
                newQuestion = random()
            }
            currentQuestion = newQuestion
            banList.add(currentQuestion)
            mutableRandomQuestion.postValue(
                randomQuestion.value?.copy(
                    question = currentQuestion.question,
                    answer = null,
                    count = getCountText(questionCount),
                    sortButtonText = getSortButtonText(),
                    switchQAButtonText = getSwitchQAButtonText()
                )
            )
        } catch (e: Exception) {
            mutableRandomQuestion.postValue(
                GameUiModel(question = "Bienvenue", sortButtonText = 0, switchQAButtonText = 0)
            )
        }
    }

    private fun List<QuestionUiModel>.getNext() {
        questionCount += 1
        if (questionCount >= size) {
            questionCount = 0
        }
        getOrNull(questionCount)?.let {
            currentQuestion = it
            mutableRandomQuestion.postValue(
                randomQuestion.value?.copy(
                    question = currentQuestion.question,
                    answer = null,
                    count = getCountText(questionCount),
                    sortButtonText = getSortButtonText()
                )
            )
        }
    }

    private fun List<QuestionUiModel>.filterPlayable(): List<QuestionUiModel> {
        return toMutableList().filter { it.isPlayable }.toList()
    }

    private fun isBanListFull(listSize: Int): Boolean {
        return banList.size >= BAN_LIST_SIZE ||
                banList.size >= listSize
    }

    private fun getSortButtonText() = if (sortMode == SortMode.RANDOM)
        R.string.sort_by_rand
    else R.string.sort_by_order

    private fun getSwitchQAButtonText() = if (qa == GameMode.IMAGE_FIRST)
        R.string.image_first
    else R.string.memory_first

    private fun toggleSortMode() {
        sortMode = if (sortMode == SortMode.RANDOM)
            SortMode.ORDINAL
        else SortMode.RANDOM
    }

    companion object {
        private const val BAN_LIST_SIZE = 3
        private fun getCountText(count: Int) = "entrées : $count"
    }

    private enum class SortMode {
        ORDINAL,
        RANDOM
    }

    private enum class GameMode {
        IMAGE_FIRST,
        MEMORY_FIRST
    }

    interface IState<T> {
        fun getNext(): T
        fun getFirst(): T
    }

    sealed class State : IState<State> {
        override fun getFirst(): State = Question

        object Question : State(), IState<State> {
            override fun getNext(): State = Answer
        }

        object Answer : State(), IState<State> {
            override fun getNext(): State = Question
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

data class GameUiModel(
    val question: String,
    val answer: String? = null,
    val count: String = "",
    @StringRes
    val sortButtonText: Int,
    @StringRes
    val switchQAButtonText: Int
)
