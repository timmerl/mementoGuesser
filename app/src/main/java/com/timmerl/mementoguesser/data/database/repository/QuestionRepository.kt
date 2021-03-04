package com.timmerl.mementoguesser.data.database.repository

import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.mapper.toEntity
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class QuestionRepositoryImpl(private val dao: QuestionDao) : QuestionRepository {

    override fun getAll() =
        dao.getAll().toModel()

    override fun getAllActive(sorted: Boolean) =
        dao.getAll()
            .map { list ->
                if (sorted)
                    list.filter { item ->
                        item.isPlayable
                    }.sortedBy {
                        try {
                            it.question.toInt()
                        } catch (e: NumberFormatException) {
                            0
                        }
                    }
                else list
            }.toModel()

    override suspend fun insert(question: String, answer: String) =
        dao.insert(QuestionEntity(question = question, answer = answer, isPlayable = true))

    override suspend fun update(question: Question) {
        dao.update(question.toEntity())
    }

    override suspend fun toggleIsPlayable(question: Question) {
        dao.update(question.id, !question.isPlayable)
    }

    override fun delete(question: Question) = dao.delete(question.id)

    private fun Flow<List<Question>>.fakeIt(size: Int) = map {
        it.toMutableList().apply {
            addAll(List(size) { idx ->
                Question(idx, "-> $idx", "", true)
            })
        }
    }
}
