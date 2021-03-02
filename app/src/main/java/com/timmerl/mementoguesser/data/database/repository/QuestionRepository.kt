package com.timmerl.mementoguesser.data.database.repository

import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class QuestionRepositoryImpl(private val dao: QuestionDao) : QuestionRepository {

    override fun getAll() = dao.getAll().toModel().map {
        it.toMutableList().apply {
            addAll(
                List<Question>(25) { idx ->
                    Question(idx, "-> $idx", "")
                })
        }
    }


    override suspend fun insert(question: String, answer: String) =
        dao.insert(QuestionEntity(question, answer))

    override fun delete(question: Question) = dao.delete(question.toEntity())

    private fun Flow<List<QuestionEntity>>.toModel() =
        map { list ->
            list.map { item ->
                Question(
                    id = item.id,
                    question = item.question,
                    answer = item.answer
                )
            }
        }

    private fun Question.toEntity() = QuestionEntity(question, answer)
}
