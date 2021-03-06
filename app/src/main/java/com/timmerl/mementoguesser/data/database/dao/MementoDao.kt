package com.timmerl.mementoguesser.data.database.dao

import androidx.room.*
import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MementoEntity
import com.timmerl.mementoguesser.data.database.entity.MemoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

@Dao
interface MementoDao {
    @Transaction
    @Query("SELECT * FROM MemoryEntity")
    fun getMementoFlow(): Flow<List<MementoEntity>>

    @Query("SELECT * FROM MemoryEntity")
    suspend fun getMementos(): List<MementoEntity>

    @Query("SELECT * FROM MemoryEntity WHERE id = :id")
    suspend fun getMemento(id: Long): MementoEntity

    @Query("SELECT * FROM ImageEntity WHERE id = :id")
    suspend fun getImage(id: Long): ImageEntity

    @Transaction
    @Query("SELECT * FROM ImageEntity")
    suspend fun getImages(): List<ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memory: MemoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: ImageEntity)

    @Query("UPDATE ImageEntity SET isPlayable = :isPlayable WHERE id =:id")
    fun update(id: Long, isPlayable: Boolean)

    @Query("DELETE FROM ImageEntity WHERE id =:id")
    fun deleteImage(id: Long)

    @Query("DELETE FROM MemoryEntity WHERE id =:id")
    fun deleteMemory(id: Long)
}