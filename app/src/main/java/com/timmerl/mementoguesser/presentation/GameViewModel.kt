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

    private var currentQuestion = Question(-1, "", "")

    private var questionBackStack = mutableListOf(currentQuestion)

    private val mutableRandomQuestion = MutableLiveData(GameUiModel("waiting"))
    val randomQuestion: LiveData<GameUiModel> = mutableRandomQuestion

    init {
        getRandomQuestion()
    }

    fun createQuestion(question: String, answer: String) {
        viewModelScope.launch(Dispatchers.IO) {
            rep.insert(question, answer)
        }
    }

    fun getRandomQuestion() {
        viewModelScope.launch(Dispatchers.IO) {
            rep.getAll().collect { questions ->
                try {
                    var newQuestion = questions.random()
                    if (isBackStackFull(questions.size))
                        questionBackStack.clear()

                    while (questionBackStack.contains(newQuestion)) {
                        newQuestion = questions.random()
                    }
                    currentQuestion = newQuestion
                    questionBackStack.add(currentQuestion)
                    mutableRandomQuestion.postValue(
                        GameUiModel(question = currentQuestion.question)
                    )
                } catch (e: Exception) {
                    mutableRandomQuestion.postValue(
                        GameUiModel("Ajoute des questions")
                    )
                }
            }
        }
    }

    fun getAnswer() {
        mutableRandomQuestion.postValue(
            GameUiModel(
                question = currentQuestion.question,
                answer = currentQuestion.answer
            )
        )
    }

    private fun isBackStackFull(listSize: Int): Boolean {
        return questionBackStack.size >= MAX_BACKSTACK_SIZE ||
                questionBackStack.size >= listSize
    }

    companion object {
        private const val MAX_BACKSTACK_SIZE = 10
    }
}

data class GameUiModel(val question: String, val answer: String? = null)
