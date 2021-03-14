package com.timmerl.mementoguesser.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.timmerl.mementoguesser.data.database.AnswerListConverters
import java.util.*

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Entity
class QuestionEntity(
    @ColumnInfo(name = "question") val question: String,
    @TypeConverters(AnswerListConverters::class)
    @ColumnInfo(name = "answers") val answers: List<String>,
    @ColumnInfo(name = "isPlayable") val isPlayable: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuestionEntity

        if (question != other.question) return false
        if (answers != other.answers) return false
        if (isPlayable != other.isPlayable) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = Objects.hash(id, question, answers, isPlayable)
}