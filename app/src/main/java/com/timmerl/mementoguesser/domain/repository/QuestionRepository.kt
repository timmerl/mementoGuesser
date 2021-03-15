package com.timmerl.mementoguesser.domain.repository

import com.timmerl.mementoguesser.domain.model.Answer
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

interface QuestionRepository {
    fun getAll(): Flow<List<Memento>>

    suspend fun getAllDirect(): List<Memento>
    suspend fun getAllAnswers(): List<Answer>

    suspend fun insertQuestion(question: String): Long
    suspend fun insertAnswer(mementoId: Long, answer: String)
    suspend fun update(mementoId: Long, isPlayable: Boolean)

    fun delete(mementoId: Long)
}