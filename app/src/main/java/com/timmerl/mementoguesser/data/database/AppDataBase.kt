package com.timmerl.mementoguesser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timmerl.mementoguesser.data.database.dao.QuestionDao
import com.timmerl.mementoguesser.data.database.entity.AnswerEntity
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Database(
    entities = [QuestionEntity::class, AnswerEntity::class],
    exportSchema = false,
    version = 3
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}