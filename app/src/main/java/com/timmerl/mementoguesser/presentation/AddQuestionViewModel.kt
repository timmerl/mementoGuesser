package com.timmerl.mementoguesser.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class AddQuestionViewModel(
    private val rep: QuestionRepository
) : ViewModel() {

    fun createQuestion(question: String, answer: String): Boolean {
        if (question.isBlank() || answer.isBlank()) {
            return false
        }
        viewModelScope.launch(Dispatchers.IO) {
            rep.insert(
                question,
                answer
            )
        }
        return true
    }
}