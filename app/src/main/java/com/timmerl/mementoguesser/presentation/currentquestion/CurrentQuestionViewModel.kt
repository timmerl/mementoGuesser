package com.timmerl.mementoguesser.presentation.currentquestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class CurrentQuestionViewModel(
    private val rep: QuestionRepository
) : ViewModel() {


    private var sortMode = SortMode.RANDOM
    private var currentQuestion = QuestionUiModel(
        id = -1,
        question = "",
        answer = "",
        isPlayable = false,
        showMenu = false
    )
    private var questionCount = 0

    private var banList = mutableListOf(currentQuestion)

    private val mutableRandomQuestion = MutableLiveData(
        GameUiModel(question = "waiting", sortButtonText = 0)
    )
    val randomQuestion: LiveData<GameUiModel> = mutableRandomQuestion

    init {
        getQuestion()
    }

    fun getQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            rep.getAllActive(true).map {
                it.map { q ->
                    QuestionUiModel(
                        id = q.id,
                        question = q.question,
                        answer = q.answer,
                        isPlayable = q.isPlayable,
                        showMenu = false
                    )
                }
            }.collect { questions ->
                when (sortMode) {
                    SortMode.RANDOM -> questions.getRandom()
                    SortMode.ORDINAL -> questions.getNext()
                }
            }
        }
    }

    fun toggleSorting() {
        questionCount = 0
        sortMode = if (sortMode == SortMode.RANDOM) {
            SortMode.ORDINAL
        } else {
            SortMode.RANDOM
        }
        banList.clear()
        getQuestion()
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
        currentQuestion = get(questionCount)
        mutableRandomQuestion.postValue(
            randomQuestion.value?.copy(
                question = currentQuestion.question,
                answer = null,
                count = getCountText(questionCount),
                sortButtonText = getSortButtonText()
            )
        )
    }

    fun getAnswer() {
        mutableRandomQuestion.postValue(
            randomQuestion.value?.copy(
                answer = currentQuestion.answer
            )
        )
    }

    private fun isBanListFull(listSize: Int): Boolean {
        return banList.size >= BAN_LIST_SIZE ||
                banList.size >= listSize
    }

    private fun getSortButtonText() = if (sortMode == SortMode.RANDOM)
        R.string.sort_by_order
    else R.string.sort_by_rand

    companion object {
        private const val BAN_LIST_SIZE = 3
        private fun getCountText(count: Int) = "entrées : $count"
    }

    private enum class SortMode {
        ORDINAL,
        RANDOM
    }
}

data class GameUiModel(
    val question: String,
    val answer: String? = null,
    val count: String = "",
    val sortButtonText: Int
)
