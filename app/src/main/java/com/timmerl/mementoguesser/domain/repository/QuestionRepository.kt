package com.timmerl.mementoguesser.domain.repository

import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

interface QuestionRepository {
    fun getMementoFlow(): Flow<List<Memento>>

    suspend fun getMementos(): List<Memento>
    suspend fun getImages(): List<Image>

    suspend fun insertMemory(memory: String): Long
    suspend fun insertImage(mementoId: Long, imageName: String)
    suspend fun update(mementoId: Long, isPlayable: Boolean)

    suspend fun deleteMemento(imageId: Long)
}