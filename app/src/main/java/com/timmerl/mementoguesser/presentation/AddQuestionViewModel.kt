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
            // todo fetch
            uiMutableLiveData.postValue(AddQuestionUiModel("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.journaldugeek.com%2F2021%2F01%2F09%2Fla-maison-dedition-de-dragon-ball-interdit-le-partage-dimages-et-de-gif-du-manga%2F&psig=AOvVaw2ardldBl4Bi45nCnhhEZBJ&ust=1614690010365000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCPjG_5mTj-8CFQAAAAAdAAAAABAP"))
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