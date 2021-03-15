package com.timmerl.mementoguesser.domain.adapter

import com.timmerl.mementoguesser.domain.adapter.MementoInteractor.Companion.SortType
import com.timmerl.mementoguesser.domain.mapper.shuffled
import com.timmerl.mementoguesser.domain.mapper.sortByOrdinal
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

class MementoInteractorImpl(
    private val repository: QuestionRepository
) : MementoInteractor {

    private val mementoFlow = repository.getAll()

    override fun getMementos(sortedBy: SortType): Flow<List<Memento>> {
        return when (sortedBy) {
            SortType.ORDINAL -> mementoFlow.sortByOrdinal()
            SortType.RANDOM -> mementoFlow.shuffled()
        }
    }

    override suspend fun addMemento(question: String, answer: String) {
        val memento = repository.getAllDirect().find { it.question == question }
        if (memento != null) {
            repository.insertAnswer(memento.id, answer)
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

}