package com.timmerl.mementoguesser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val uiMutableLiveData = MutableLiveData(
        AddQuestionUiModel(null)
    )
    val uiLiveData: LiveData<AddQuestionUiModel> = uiMutableLiveData

    fun findImage(answer: String) {
        if (answer.isNotBlank()) {

            uiMutableLiveData.postValue(AddQuestionUiModel("https://www.journaldugeek.com/content/uploads/2021/01/dragon-ball-4995675-1280-640x492.jpg"))
        }
    }

    fun createQuestion(question: String, answer: String): Boolean {
        if (question.isBlank() || answer.isBlank()) {
            return false
        }
        viewModelScope.launch(Dispatchers.IO) {
            rep.insert(
                question,
                answer,
                uiLiveData.value?.imageUrl ?: ""
            )
        }
        return true
    }

    fun reset() {
        uiMutableLiveData.postValue(AddQuestionUiModel(null))
    }

    class AddQuestionUiModel(
        val imageUrl: String?
    )
}