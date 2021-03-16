package com.timmerl.mementoguesser.domain.mapper

import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MementoEntity
import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Timmerman_Lyderic on 03/03/2021.
 */


fun ImageEntity.toModel() = Image(
    name = name, isPlayable = isPlayable, id = id, mementoId = mementoId,
)

fun List<ImageEntity>.toModel() = map { it.toModel() }

fun List<MementoEntity>.mapToModel() = mutableListOf<Memento>().apply {
    this@mapToModel.forEach { addAll(it.toModel()) }
}.toList()


fun Flow<List<MementoEntity>>.toModel() = map { it.mapToModel() }

fun MementoEntity.toModel() = mutableListOf<Memento>()
    .apply {
        this@toModel.images.forEach {
            add(
                Memento(
                    id = this@toModel.memory.id,
                    memory = this@toModel.memory.name,
                    image = Image(
                        name = it.name,
                        isPlayable = it.isPlayable,
                        id = it.id,
                        mementoId = it.mementoId
                    )

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
