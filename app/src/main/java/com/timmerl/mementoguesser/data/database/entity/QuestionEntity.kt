package com.timmerl.mementoguesser.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Entity
data class QuestionEntity(
    @ColumnInfo(name = "question") val question: String,
    @ColumnInfo(name = "answer") val answer: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}