package com.timmerl.mementoguesser.domain.adapter

import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

interface MementoAdapter {
    fun getMementoFlow(sortedBy: SortType, showNonPlayable: Boolean): Flow<List<Memento>>
    suspend fun getMementos(sortedBy: SortType): List<Memento>

    suspend fun addMemento(memory: String, image: String)

    suspend fun togglePlayableForId(imageId: Long)

    suspend fun delete(mementoId: Long)

    companion object {
        enum class SortType {
            ORDINAL,
            RANDOM
        }
    }
}