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

    @Query("SELECT * FROM QuestionEntity WHERE isPlayable LIKE 'true'")
    fun getAllActive(): Flow<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity)

    @Query("UPDATE QuestionEntity SET isPlayable = :isPlayable WHERE id =:id")
    fun update(id: Int, isPlayable: Boolean)

    @Update
    suspend fun update(question: QuestionEntity)

    @Delete
    fun delete(question: QuestionEntity)
}