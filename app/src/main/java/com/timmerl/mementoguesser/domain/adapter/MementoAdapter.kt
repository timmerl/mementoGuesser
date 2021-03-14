package com.timmerl.mementoguesser.domain.adapter

import android.util.Log
import com.timmerl.mementoguesser.domain.adapter.MementoInteractor.Companion.SortType
import com.timmerl.mementoguesser.domain.mapper.shuffled
import com.timmerl.mementoguesser.domain.mapper.sortByOrdinal
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

class MementoAdapter(
    private val repository: QuestionRepository
) : MementoInteractor {

    private val mementoFlow = repository.getAll()

    override fun getMementos(sortedBy: SortType, showNonPlayable: Boolean): Flow<List<Memento>> {
        return when (sortedBy) {
            SortType.ORDINAL -> mementoFlow.sortByOrdinal()
            SortType.RANDOM -> mementoFlow.shuffled()
        }.map {
            it.filterPlayable(showNonPlayable).separatesAnswers()
        }
    }

    override suspend fun addMemento(question: String, answer: String) {
        Log.e("addMemento", "call collect")
        when (val memento = repository.getAllDirect().find { it.question == question }) {
            is Memento -> repository.update(memento.id, memento.answers + answer)
            else -> repository.insert(question, listOf(answer))
        }
    }

    override suspend fun togglePlayableForId(mementoId: Int) {
        repository.getAllDirect().find { it.id == mementoId }?.let {
            repository.update(it.id, it.isPlayable.not())
        }
    }

    override fun delete(mementoId: Int) = repository.delete(mementoId)

    private fun List<Memento>.filterPlayable(showNonPlayable: Boolean): List<Memento> {
        Log.e("MementoAdapter", "filterPlayable.size(${size}")
        return if (showNonPlayable) this
        else toMutableList().filter { it.isPlayable }
    }

    private fun List<Memento>.separatesAnswers(): List<Memento> {
        Log.e("MementoAdapter", "separatesAnswer.size(${size}")
        return mutableListOf<Memento>().apply {
            this@separatesAnswers.forEach { memento ->
                for (answer in memento.answers) {
                    add(memento.copy(answers = listOf(answer)))
                }

            }
            Log.e("MementoAdapter", "separatesAnswer.size(${size}")
        }.toList()
    }
}