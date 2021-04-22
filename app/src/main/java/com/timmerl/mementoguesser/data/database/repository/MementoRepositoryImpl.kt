package com.timmerl.mementoguesser.data.database.repository

import com.timmerl.mementoguesser.data.database.dao.MementoDao
import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MemoryEntity
import com.timmerl.mementoguesser.domain.mapper.mapToModel
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.MementoRepository

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoRepositoryImpl(private val dao: MementoDao) : MementoRepository {

    override fun getMementoFlow() =
        dao.getMementoFlow().toModel()

    override suspend fun getMementos(): List<Memento> =
        dao.getMementos().mapToModel()

    override suspend fun getMemento(mementoId: Long, imageId: Long): Memento =
        dao.getMemento(mementoId).toModel().first { it.image.id == imageId }

    override suspend fun insertMemory(memory: String) =
        dao.insert(MemoryEntity(name = memory))

    override suspend fun getImages(): List<Image> {
        return dao.getImages().toModel()
    }

    override suspend fun insertImage(mementoId: Long, imageName: String, isPlayable: Boolean) {
        dao.insert(
            ImageEntity(
                name = imageName,
                mementoId = mementoId,
                isPlayable = isPlayable
            )
        )
    }

    override suspend fun update(mementoId: Long, isPlayable: Boolean) {
        dao.update(mementoId, isPlayable)
    }

    override suspend fun deleteMemento(imageId: Long) {
        val mementoId = dao.getImage(imageId).mementoId
        val mementoHasNoImage = dao.getMemento(mementoId).images.isEmpty()
        dao.deleteImage(imageId)
        if (mementoHasNoImage)
            dao.deleteMemory(mementoId)
    }

}
