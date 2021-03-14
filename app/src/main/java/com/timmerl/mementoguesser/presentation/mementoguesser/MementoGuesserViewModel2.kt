package com.timmerl.mementoguesser.presentation.mementoguesser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.mapper.random
import com.timmerl.mementoguesser.domain.mapper.sortByOrdinal
import com.timmerl.mementoguesser.domain.mapper.toUiModel
import com.timmerl.mementoguesser.domain.repository.QuestionRepository

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoGuesserViewModel2(
    private val rep: QuestionRepository
) : ViewModel() {

    private val questionList = rep.getAll().apply {
        if (sortMode == SortMode.ORDINAL) {
            sortByOrdinal()
        } else {
            random()
        }
    }.toUiModel().asLiveData(viewModelScope.coroutineContext)

    private var sortMode = SortMode.ORDINAL
    private var state: State = State.NewQuestion

    private fun getSortButtonText() = if (sortMode == SortMode.RANDOM)
        R.string.sort_by_order
    else R.string.sort_by_rand

    private fun toggleSortMode() {
        sortMode = if (sortMode == SortMode.RANDOM)
            SortMode.ORDINAL
        else SortMode.RANDOM
    }

    companion object {
        private const val INCOMING_LIST_SIZE = 3
        private fun getCountText(count: Int) = "entrées : $count"
    }

    private enum class SortMode {
        ORDINAL,
        RANDOM
    }

    interface IState {
        fun getNext(): State
        fun getFirst(): State = State.NewQuestion
    }

    sealed class State : IState {
        object ShowAnswer : State(), IState {
            override fun getNext(): State = NewQuestion
        }

        object NewQuestion : State(), IState {
            override fun getNext(): State = ShowAnswer
        }
    }
}

data class GameUiModel2(
    val question: String,
    val answer: String? = null,
    val count: String = "",
    val sortButtonText: Int
)
