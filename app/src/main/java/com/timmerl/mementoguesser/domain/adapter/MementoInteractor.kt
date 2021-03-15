package com.timmerl.mementoguesser.domain.adapter

import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 14/03/2021.
 */

interface MementoInteractor {
    fun getMementos(sortedBy: SortType): Flow<List<Memento>>

    suspend fun addMemento(question: String, answer: String)

    suspend fun togglePlayableForId(answerId: Long)

    fun delete(mementoId: Long)

    companion object {
        enum class SortType {
            ORDINAL,
            RANDOM
        }
    }
}