package com.timmerl.mementoguesser.domain.mapper

import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MementoEntity
import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 03/03/2021.
 */


fun ImageEntity.toModel() = Image(
    name = name, isPlayable = isPlayable, id = id, mementoId = mementoId,
)

fun List<ImageEntity>.toModel() = map { it.toModel() }
fun List<MementoEntity>.mapToModel() = map { it.toModel() }

fun MementoEntity.toModel() = Memento(
    id = memory.id,
    memory = memory.name,
    images = images.map { it.toModel() }
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
        this@toUiModel.images.forEach {
            add(
                QuestionUiModel(
                    mementoId = this@toUiModel.id,
                    answerId = it.id,
                    question = this@toUiModel.memory,
                    answer = it.name,
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
                item.memory.toInt()
            } catch (e: NumberFormatException) {
                0
            }
        }
    }

fun Flow<List<Memento>>.random() = map { it.random() }