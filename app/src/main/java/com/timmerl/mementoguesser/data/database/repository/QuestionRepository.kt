package com.timmerl.mementoguesser.data.database.repository

import android.util.Log
import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.entity.AnswerEntity
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.mapper.mapToModel
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.model.Answer
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class QuestionRepositoryImpl(private val dao: QuestionDao) : QuestionRepository {

    override fun getAll() =
        dao.getAll().toModel()

    override suspend fun getAllDirect(): List<Memento> =
        dao.getAllDirect().mapToModel()

    override suspend fun insertQuestion(question: String) =
        dao.insert(
            QuestionEntity(
                question = question
            )
        )

    override suspend fun getAllAnswers(): List<Answer> {
        return dao.getAllAnswers().toModel()
    }

    override suspend fun insertAnswer(mementoId: Long, answer: String) {
        dao.insert(
            AnswerEntity(
                answer = answer,
                mementoId = mementoId,
                isPlayable = true
            )
        )
    }

    override suspend fun update(mementoId: Long, isPlayable: Boolean) {
        Log.e("repository", "update isPlayable")
        dao.update(mementoId, isPlayable)
    }

    override fun delete(mementoId: Long) = dao.delete(mementoId)

}
