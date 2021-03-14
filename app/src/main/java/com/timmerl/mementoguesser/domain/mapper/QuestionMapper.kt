package com.timmerl.mementoguesser.domain.mapper

import android.util.Log
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 03/03/2021.
 */

fun QuestionEntity.toModel() = Memento(
    id = id,
    question = question,
    answers = answers,
    isPlayable = isPlayable
)

fun List<QuestionEntity>.toModel() = map { it.toModel() }

fun Flow<List<QuestionEntity>>.toModel() =
    map { it.toModel() }


fun Memento.toEntity() = QuestionEntity(question, answers, isPlayable)

fun Memento.toUiModel() = QuestionUiModel(
    id = id,
    question = question,
    answer = answers.first(),
    isPlayable = isPlayable,
    showMenu = false
)

fun List<Memento>.toUiModel() = map { it.toUiModel() }

fun Flow<List<Memento>>.toUiModel() = map { it.toUiModel() }

fun Flow<List<Memento>>.shuffled() = map { it.shuffled() }

fun Flow<List<Memento>>.sortByOrdinal() =
    map {
        Log.e("MementoAdapter", "sortByOrdinal.size(${it.size}")
        it.sortedBy { item ->
            try {
                item.question.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
    }

fun Flow<List<Memento>>.random() = map { it.random() }