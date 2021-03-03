package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    private val rep: QuestionRepository
) : ViewModel() {

    val questionList = rep.getAll().asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: Question) = viewModelScope.launch(Dispatchers.IO) {
        rep.update(question.copy(isPlayable = question.isPlayable.not()))
    }

}