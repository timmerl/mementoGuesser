package com.timmerl.mementoguesser.domain.mapper

import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 03/03/2021.
 */

fun QuestionEntity.toModel() = Question(
    id = id,
    question = question,
    answer = answer,
    isPlayable = isPlayable
)

fun Flow<List<QuestionEntity>>.toModel() =
    map { list -> list.map { it.toModel() } }


fun Question.toEntity() = QuestionEntity(question, answer, isPlayable)

fun Question.toUiModel() = QuestionUiModel(
    id = id,
    question = question,
    answer = answer,
    isPlayable = isPlayable,
    showMenu = false
)

fun List<Question>.toUiModel() = map { it.toUiModel() }

fun Flow<List<Question>>.toUiModel() =
    map { list -> list.map { it.toUiModel() } }

fun Flow<List<Question>>.sortByOrdinal() =
    map {
        it.sortedBy { item ->
            try {
                item.question.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
    }

fun Flow<List<Question>>.random() = map { it.random() }

fun QuestionUiModel.toModel() = Question(id, question, answer, isPlayable)
