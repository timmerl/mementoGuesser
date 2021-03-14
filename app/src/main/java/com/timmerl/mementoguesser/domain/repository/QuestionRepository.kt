package com.timmerl.mementoguesser.domain.repository

import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

interface QuestionRepository {
    fun getAll(): Flow<List<Memento>>

    suspend fun getAllDirect(): List<Memento>

    suspend fun insert(question: String, answers: List<String>)

    suspend fun update(mementoId: Int, isPlayable: Boolean)

    suspend fun update(id: Int, answers: List<String>)

    fun delete(mementoId: Int)
}