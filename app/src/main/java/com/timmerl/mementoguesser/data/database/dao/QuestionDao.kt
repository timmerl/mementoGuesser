package com.timmerl.mementoguesser.data.database.dao

import androidx.room.*
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Dao
interface QuestionDao {
    @Query("SELECT * FROM QuestionEntity")
    fun getAll(): Flow<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity)

    @Delete
    fun delete(question: QuestionEntity)
}