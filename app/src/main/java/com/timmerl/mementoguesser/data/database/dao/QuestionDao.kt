package com.timmerl.mementoguesser.data.database.dao

import androidx.room.*
import com.timmerl.mementoguesser.data.database.entity.AnswerEntity
import com.timmerl.mementoguesser.data.database.entity.MementoEntity
import com.timmerl.mementoguesser.data.database.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Dao
interface QuestionDao {
    @Transaction
    @Query("SELECT * FROM QuestionEntity")
    fun getAll(): Flow<List<MementoEntity>>

    @Transaction
    @Query("SELECT * FROM QuestionEntity")
    suspend fun getAllDirect(): List<MementoEntity>

    @Transaction
    @Query("SELECT * FROM AnswerEntity")
    suspend fun getAllAnswers(): List<AnswerEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: AnswerEntity)

    @Query("UPDATE AnswerEntity SET isPlayable = :isPlayable WHERE id =:id")
    fun update(id: Long, isPlayable: Boolean)

    @Query("DELETE FROM AnswerEntity WHERE id =:id")
    fun delete(id: Long)
}