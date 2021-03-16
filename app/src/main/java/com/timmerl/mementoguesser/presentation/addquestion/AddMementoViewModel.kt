package com.timmerl.mementoguesser.presentation.addquestion

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

    fun createMemento(question: String, answer: String): Boolean {
        if (question.isBlank() || answer.isBlank()) {
            return false
        }
        viewModelScope.launch(Dispatchers.IO) {
            adapter.addMemento(question, answer)
        }
        return true
    }
}