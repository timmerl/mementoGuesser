package com.timmerl.mementoguesser.presentation.addquestion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.domain.adapter.MementoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class AddMementoViewModel(
    private val adapter: MementoAdapter
) : ViewModel() {

    private val mutableEvent = MutableLiveData(false)
    val event: LiveData<Boolean> = mutableEvent

    fun createMemento(memory: String, image: String) {
        if (memory.isBlank() || image.isBlank()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            adapter.addMemento(memory, image)
            mutableEvent.postValue(event.value?.not())
        }
    }
}