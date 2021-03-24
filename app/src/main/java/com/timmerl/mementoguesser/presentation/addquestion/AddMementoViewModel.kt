package com.timmerl.mementoguesser.presentation.addquestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.presentation.utils.UiEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class AddMementoViewModel(
    private val adapter: MementoAdapter
) : ViewModel() {

    private val _uiState = MutableLiveData<UiEvent<AddMementoAction>>()
    val uiState: LiveData<UiEvent<AddMementoAction>>
        get() = _uiState
    private var count = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.postValue(UiEvent.create(AddMementoAction.NewMemento(count++)))
        }
    }

    fun createMemento(memory: String, image: String) {
        if (memory.isBlank() || image.isBlank()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            adapter.addMemento(memory, image)
            _uiState.postValue(
                UiEvent.create(
                    AddMementoAction.NewMemento(count++)
                )
            )
        }
    }
}

sealed class AddMementoAction {
    data class NewMemento(val count: Int) : AddMementoAction()
}
