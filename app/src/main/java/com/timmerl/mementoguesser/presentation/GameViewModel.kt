package com.timmerl.mementoguesser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class GameViewModel(
    private val rep: QuestionRepository
) : ViewModel() {

    private var currentQuestion = Question(-1, "", "", null)
    private var questionCount = 0

    private var banList = mutableListOf(currentQuestion)

    private val mutableRandomQuestion = MutableLiveData(GameUiModel(question = "waiting"))
    val randomQuestion: LiveData<GameUiModel> = mutableRandomQuestion

    init {
        getRandomQuestion()
    }

    fun getRandomQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            rep.getAll().collect { questions ->
                questionCount = questions.size
                try {
                    var newQuestion = questions.random()
                    if (isBanListFull(questionCount))
                        banList.removeAt(0)

                    while (banList.contains(newQuestion)) {
                        newQuestion = questions.random()
                    }
                    currentQuestion = newQuestion
                    banList.add(currentQuestion)
                    mutableRandomQuestion.postValue(
                        randomQuestion.value?.copy(
                            question = currentQuestion.question,
                            answer = null,
                            count = getCountText(questionCount)
                        )
                    )
                } catch (e: Exception) {
                    mutableRandomQuestion.postValue(
                        GameUiModel(question = "Ajoute des questions")
                    )
                }
            }
        }
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

    companion object {
        private const val BAN_LIST_SIZE = 3
        private fun getCountText(count: Int) = "entrées : $count"
    }
}

data class GameUiModel(val question: String, val answer: String? = null, val count: String = "")
