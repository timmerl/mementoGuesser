package com.timmerl.mementoguesser.presentation.view.editmemento

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.utils.UiEvent
import com.timmerl.mementoguesser.presentation.view.editmemento.EditMementoUiEvent.ActionDone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class EditMementoViewModel(
    private val mementoId: Long,
    private val imageId: Long,
    private val adapter: MementoAdapter
) : ViewModel() {

    private val _memory = MutableLiveData("")
    val memory: LiveData<String> = _memory
    private val _image = MutableLiveData("")
    val image: LiveData<String> = _image

    private val _event = MutableLiveData<UiEvent<EditMementoUiEvent>>()
    val event: LiveData<UiEvent<EditMementoUiEvent>> = _event

    private lateinit var memento: Memento

    init {
        viewModelScope.launch(Dispatchers.IO) {
            memento = adapter.getMemento(mementoId = mementoId, imageId = imageId)
            _memory.postValue(memento.memory)
            _image.postValue(memento.image.name)
        }
    }

    fun onMemoryChange(newMemory: String) {
        _memory.value = newMemory
    }

    fun onImageChange(newImage: String) {
        _image.value = newImage
    }

    fun editMemento() {
        val memory = memory.value
        val image = image.value
        if (memory.isNullOrEmpty() || image.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            adapter.editMemento(
                mementoId = mementoId,
                imageId = imageId,
                memory = memory,
                image = image
            )
            _event.postValue(UiEvent.create(ActionDone))
        }
    }
}

sealed class EditMementoUiEvent {
    object ActionDone : EditMementoUiEvent()
}
