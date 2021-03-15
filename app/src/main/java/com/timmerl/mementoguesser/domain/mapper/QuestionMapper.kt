package com.timmerl.mementoguesser.domain.mapper

import com.timmerl.mementoguesser.data.database.entity.AnswerEntity
import com.timmerl.mementoguesser.data.database.entity.MementoEntity
import com.timmerl.mementoguesser.domain.model.Answer
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 03/03/2021.
 */


fun AnswerEntity.toModel() = Answer(
    answer = answer, isPlayable = isPlayable, id = id, mementoId = mementoId,
)

fun List<AnswerEntity>.toModel() = map { it.toModel() }
fun List<MementoEntity>.mapToModel() = map { it.toModel() }

fun MementoEntity.toModel() = Memento(
    id = question.id,
    question = question.question,
    answers = answers.map { it.toModel() }
)

fun Flow<List<MementoEntity>>.toModel() = map {
    it.map { memento ->
        memento.toModel()
    }
}

fun Flow<List<Memento>>.toUiModel() = map { it.toUiModel() }

fun List<Memento>.toUiModel() = mutableListOf<QuestionUiModel>()
    .apply {
        this@toUiModel.forEach { addAll(it.toUiModel()) }
    }.toList()

fun Memento.toUiModel() = mutableListOf<QuestionUiModel>()
    .apply {
        this@toUiModel.answers.forEach {
            add(
                QuestionUiModel(
                    mementoId = this@toUiModel.id,
                    answerId = it.id,
                    question = this@toUiModel.question,
                    answer = it.answer,
                    isPlayable = it.isPlayable,
                    showMenu = false,
                )
            )
        }
    }.toList()

fun Flow<List<Memento>>.shuffled() = map { it.shuffled() }

fun Flow<List<Memento>>.sortByOrdinal() =
    map {
        it.sortedBy { item ->
            try {
                item.question.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
    }

fun Flow<List<Memento>>.random() = map { it.random() }