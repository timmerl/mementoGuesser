package com.timmerl.mementoguesser.domain.adapter

import com.timmerl.mementoguesser.domain.adapter.MementoAdapter.Companion.SortType
import com.timmerl.mementoguesser.domain.mapper.shuffled
import com.timmerl.mementoguesser.domain.mapper.sortByOrdinal
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

class MementoAdapterImpl(
    private val repository: QuestionRepository
) : MementoAdapter {

    private val mementoFlow = repository.getMementoFlow()

    override fun getMementoFlow(
        sortedBy: SortType,
        showNonPlayable: Boolean
    ): Flow<List<Memento>> {
        return when (sortedBy) {
            SortType.ORDINAL -> mementoFlow.sortByOrdinal()
            SortType.RANDOM -> mementoFlow.shuffled()
        }.filterPlayable(showNonPlayable)
    }

    private fun Flow<List<Memento>>.filterPlayable(showNonPlayable: Boolean) = map {
        it.toMutableList().filter { memento ->
            if (showNonPlayable) true
            else memento.image.isPlayable
        }.toList()
    }

    override suspend fun getMementos(sortedBy: SortType) = when (sortedBy) {
        SortType.RANDOM -> repository.getMementos().shuffled()
        SortType.ORDINAL -> repository.getMementos().sortByOrdinal()
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

    override suspend fun delete(mementoId: Long) = repository.deleteMemento(mementoId)

}