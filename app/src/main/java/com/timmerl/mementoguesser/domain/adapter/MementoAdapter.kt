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

    override fun getMementos(sortedBy: SortType): Flow<List<Memento>> {
        return when (sortedBy) {
            SortType.ORDINAL -> mementoFlow.sortByOrdinal()
            SortType.RANDOM -> mementoFlow.shuffled()
        }.map {
            it.separatesAnswers()
        }
    }

    override suspend fun addMemento(question: String, answer: String) {
        Log.e("addMemento", "call collect")

        val let = repository.getAllDirect().find { it.question == question }
        if (let != null) {
            repository.insertQuestion(question)
        } else {
            with(repository.insertQuestion(question)) {
                repository.insertAnswer(this, answer)
            }
        }
    }

    override suspend fun togglePlayableForId(answerId: Long) {
        repository.getAllAnswers().find { it.id == answerId }?.let {
            repository.update(it.id, it.isPlayable.not())
        }
    }

    override fun delete(mementoId: Long) = repository.delete(mementoId)

    private fun List<Memento>.separatesAnswers(): List<Memento> {
        Log.e("MementoAdapter", "separatesAnswerStarts.size(${size}")
        return mutableListOf<Memento>().apply {
            this@separatesAnswers.forEach { memento ->
                for (answer in memento.answers) {
                    add(memento.copy(answers = listOf(answer)))
                }

            }
            Log.e("MementoAdapter", "separatesAnswerEnds.size(${size}")
        }.toList()
    }
}