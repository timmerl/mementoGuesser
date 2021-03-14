package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.mapper.toUiModel
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    private val adapter: MementoInteractor,
) : ViewModel() {

    val questionList =
        adapter.getMementos(ORDINAL, true)
            .toUiModel()
            .asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: QuestionUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.togglePlayableForId(question.id)
        }

    fun remove(question: QuestionUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.delete(question.id)
        }

}