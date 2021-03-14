package com.timmerl.mementoguesser.presentation.mementoguesser

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
    private var state: State = State.NewQuestion
    private var currentQuestion = QuestionUiModel(
        id = -1,
        question = "",
        answer = "",
        isPlayable = false,
        showMenu = false
    )
    private var questionCount = -1

    private var banList = mutableListOf(currentQuestion)
    private val mutableRandomQuestion = MutableLiveData(
        GameUiModel(question = "waiting", sortButtonText = 0)
    )
    val randomQuestion: LiveData<GameUiModel> = mutableRandomQuestion


    init {
        continueGame()
    }

    fun continueGame() {
        when (state) {
            State.ShowAnswer -> getAnswer()
            State.NewQuestion -> getQuestion()
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
                    sortButtonText = getSortButtonText()
                )
            )
        } catch (e: Exception) {
            mutableRandomQuestion.postValue(
                GameUiModel(question = "Bienvenue", sortButtonText = 0)
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

    private fun isBanListFull(listSize: Int): Boolean {
        return banList.size >= BAN_LIST_SIZE ||
                banList.size >= listSize
    }

    private fun getSortButtonText() = if (sortMode == SortMode.RANDOM)
        R.string.sort_by_order
    else R.string.sort_by_rand

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

    interface IState {
        fun getNext(): State
        fun getFirst(): State = State.NewQuestion
    }

    sealed class State : IState {
        object ShowAnswer : State(), IState {
            override fun getNext(): State = NewQuestion
        }

        object NewQuestion : State(), IState {
            override fun getNext(): State = ShowAnswer
        }
    }
}

data class GameUiModel(
    val question: String,
    val answer: String? = null,
    val count: String = "",
    val sortButtonText: Int
)
