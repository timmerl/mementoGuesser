package com.timmerl.mementoguesser.domain.repository

import com.timmerl.mementoguesser.domain.model.Question
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

interface QuestionRepository {
    fun getAll(): Flow<List<Question>>

    suspend fun insert(question: String, answer: String, url: String?)

    fun delete(question: Question)
}