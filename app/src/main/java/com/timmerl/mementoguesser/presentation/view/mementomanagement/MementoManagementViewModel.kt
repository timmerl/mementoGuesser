package com.timmerl.mementoguesser.presentation.view.mementomanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType.ORDINAL
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _revealedCardIdsList = MutableStateFlow(listOf<Long>())
    val revealedCardIdsList: StateFlow<List<Long>> get() = _revealedCardIdsList

    fun toggleIsPlayable(question: MementoListItemUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.togglePlayableForId(question.imageId)
        }

    fun remove(question: MementoListItemUiModel) =
        viewModelScope.launch(Dispatchers.IO) {
            adapter.delete(question.imageId)
        }

    fun onItemExpanded(imageId: Long) = viewModelScope.launch(Dispatchers.IO) {

        if (_revealedCardIdsList.value.contains(imageId)) return@launch
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.add(imageId)
        }
    }

    fun onItemCollapsed(imageId: Long) = viewModelScope.launch(Dispatchers.IO) {
        if (!_revealedCardIdsList.value.contains(imageId)) return@launch
        _revealedCardIdsList.value = _revealedCardIdsList.value.toMutableList().also { list ->
            list.remove(imageId)
        }
    }

    private fun Flow<List<Memento>>.toUiModel() = map {
        mutableListOf<MementoListItemUiModel>().apply {
            it.forEach { memento ->
                add(
                    MementoListItemUiModel(
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
