package com.timmerl.mementoguesser.data.database.repository

import com.timmerl.mementoguesser.data.database.dao.MementoDao
import com.timmerl.mementoguesser.data.database.entity.ImageEntity
import com.timmerl.mementoguesser.data.database.entity.MemoryEntity
import com.timmerl.mementoguesser.domain.mapper.mapToModel
import com.timmerl.mementoguesser.domain.mapper.toModel
import com.timmerl.mementoguesser.domain.model.Image
import com.timmerl.mementoguesser.domain.model.Memento
import com.timmerl.mementoguesser.domain.repository.QuestionRepository

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

class MementoRepositoryImpl(private val dao: MementoDao) : QuestionRepository {

    override fun getMementoFlow() =
        dao.getMementoFlow().toModel()

    override suspend fun getMementos(): List<Memento> =
        dao.getMementos().mapToModel()

    override suspend fun insertMemory(memory: String) =
        dao.insert(MemoryEntity(name = memory))

    override suspend fun getImages(): List<Image> {
        return dao.getImages().toModel()
    }

    override suspend fun insertImage(mementoId: Long, imageName: String) {
        dao.insert(
            ImageEntity(
                name = imageName,
                mementoId = mementoId,
                isPlayable = true
            )
        )
    }

    override suspend fun update(mementoId: Long, isPlayable: Boolean) {
        dao.update(mementoId, isPlayable)
    }

    override suspend fun deleteMemento(imageId: Long) {
        dao.getImage(imageId).let { image ->
            dao.deleteImage(image.id)
            if (dao.getMemento(image.mementoId).images.isEmpty())
                dao.deleteMemory(image.mementoId)
        }
    }

}
