package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.model.MementoCardUiModel
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

    val questionList = adapter.getMementoFlow(ORDINAL, true)
        .toUiModel()
        .asLiveData(viewModelScope.coroutineContext)

    fun toggleIsPlayable(question: MementoCardUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.togglePlayableForId(question.imageId)
        }

    fun remove(question: MementoCardUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.delete(question.imageId)
        }

    private fun Flow<List<Memento>>.toUiModel() = map {
        mutableListOf<MementoCardUiModel>().apply {
            it.forEach { memento ->
                add(
                    MementoCardUiModel(
                        mementoId = memento.id,
                        imageId = memento.image.id,
                        memory = memento.memory,
                        image = memento.image.name,
                        isPlayable = memento.image.isPlayable
                    )
                )
            }
        }.toList()
    }
}
