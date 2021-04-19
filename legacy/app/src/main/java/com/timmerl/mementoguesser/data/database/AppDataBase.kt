package com.timmerl.mementoguesser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timmerl.mementoguesser.data.database.dao.MementoDao
import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MemoryEntity

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Database(
    entities = [MemoryEntity::class, ImageEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mementoDao(): MementoDao
}