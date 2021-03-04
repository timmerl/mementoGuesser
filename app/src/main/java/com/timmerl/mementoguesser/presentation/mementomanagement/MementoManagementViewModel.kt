package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.mapper.toUiModel
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    private val rep: QuestionRepository
) : ViewModel() {

    val questionList = rep.getAll()
        .map {
            it.sortedBy { item ->
                try {
                    item.question.toInt()
                } catch (e: NumberFormatException) {
                    0
                }
            }
        }
        .toUiModel()
        .asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: QuestionUiModel) = viewModelScope.launch(Dispatchers.IO) {
        rep.toggleIsPlayable(question.toModel())
    }

    fun remove(question: QuestionUiModel) = viewModelScope.launch(Dispatchers.IO) {
        rep.delete(question.toModel())
    }
}