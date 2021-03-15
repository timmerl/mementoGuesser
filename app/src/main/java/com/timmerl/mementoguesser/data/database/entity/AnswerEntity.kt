package com.timmerl.mementoguesser.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Entity
class AnswerEntity(
    @ColumnInfo(name = "answer") val answer: String,
    @ColumnInfo(name = "isPlayable") val isPlayable: Boolean,
    @ColumnInfo(name = "mementoId") val mementoId: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnswerEntity

        if (answer != other.answer) return false
        if (isPlayable != other.isPlayable) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = Objects.hash(id, answer, isPlayable)
}