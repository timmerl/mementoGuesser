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

    private val mementoFlow = repository.getMementoFlow()

    override fun getMementos(sortedBy: SortType): Flow<List<Memento>> {
        return when (sortedBy) {
            SortType.ORDINAL -> mementoFlow.sortByOrdinal()
            SortType.RANDOM -> mementoFlow.shuffled()
        }
    }

    override suspend fun addMemento(memory: String, image: String) {
        val memento = repository.getMementos().find { it.memory == memory }
        if (memento != null) {
            repository.insertImage(memento.id, image)
        } else {
            with(repository.insertMemory(memory)) {
                repository.insertImage(this, image)
            }
        }
    }

    override suspend fun togglePlayableForId(imageId: Long) {
        repository.getImages().find { it.id == imageId }?.let {
            repository.update(it.id, it.isPlayable.not())
        }
    }

    override fun delete(mementoId: Long) = repository.delete(mementoId)

}