package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.mapper.toUiModel
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    private val rep: QuestionRepository
) : ViewModel() {

    val questionList = rep.getAll().toUiModel()
        .asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: QuestionUiModel) = viewModelScope.launch(Dispatchers.IO) {
        rep.toggleIsPlayable(question.toModel())
    }

}