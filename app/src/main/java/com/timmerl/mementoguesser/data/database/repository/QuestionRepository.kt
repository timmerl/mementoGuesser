package com.timmerl.mementoguesser.data.database.repository

import android.util.Log
import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class QuestionRepositoryImpl(private val dao: QuestionDao) : QuestionRepository {

    override fun getAll() =
        dao.getAll().toModel()

    override suspend fun getAllDirect(): List<Memento> =
        dao.getAllDirect().toModel()

    override suspend fun insert(question: String, answers: List<String>) {
        Log.e("repository", "inserting..")
        dao.insert(
            QuestionEntity(
                question = question,
                answers = answers,
                isPlayable = true
            )
        )
    }

    override suspend fun update(id: Int, answers: List<String>) {
        Log.e("repository", "update answers")
        dao.update(id, answers)
    }

    override suspend fun update(mementoId: Int, isPlayable: Boolean) {
        Log.e("repository", "update isPlayable")
        dao.update(mementoId, isPlayable)
    }

    override fun delete(mementoId: Int) = dao.delete(mementoId)

    private fun Flow<List<Memento>>.fakeIt(size: Int) = map {
        it.toMutableList().apply {
            addAll(List(size) { idx ->
                Memento(idx, "-> $idx", listOf(""), true)
            })
        }
    }
}
