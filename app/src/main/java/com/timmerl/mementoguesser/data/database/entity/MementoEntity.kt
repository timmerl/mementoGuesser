package com.timmerl.mementoguesser.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by Timmerman_Lyderic on 15/03/2021.
 */

data class MementoEntity(
    @Embedded val memory: MemoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "mementoId"
    ) val images: List<ImageEntity>
)