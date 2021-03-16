package com.timmerl.mementoguesser.presentation.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementViewModel(
    private val adapter: MementoAdapter,
) : ViewModel() {

    val questionList =
        adapter.getMementoFlow(ORDINAL, true)
            .toUiModel()
            .asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: QuestionUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.togglePlayableForId(question.answerId)
        }

    fun remove(question: QuestionUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.delete(question.mementoId)
        }

    private fun Flow<List<Memento>>.toUiModel() = map {
        mutableListOf<QuestionUiModel>().apply {
            it.forEach { memento ->
                add(
                    QuestionUiModel(
                        mementoId = memento.id,
                        answerId = memento.image.id,
                        question = memento.memory,
                        answer = memento.image.name,
                        isPlayable = memento.image.isPlayable,
                        showMenu = false
                    )
                )
            }
        }.toList()
    }
}
