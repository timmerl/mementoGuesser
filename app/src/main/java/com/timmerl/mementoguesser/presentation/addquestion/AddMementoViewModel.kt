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

    private val _memory = MutableLiveData("")
    val memory: LiveData<String> = _memory
    private val _image = MutableLiveData("")
    val image: LiveData<String> = _image


    fun onMemoryChange(newMemory: String) {
        _memory.value = newMemory
    }

    fun onImageChange(newImage: String) {
        _image.value = newImage
    }

    fun createMemento() {
        val memory = memory.value
        val image = image.value
        if (memory.isNullOrEmpty() || image.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            adapter.addMemento(memory, image)
            _image.postValue("")
            _memory.postValue("")
        }
    }
}