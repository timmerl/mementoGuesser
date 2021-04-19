package com.timmerl.mementoguesser.domain.model

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class Memento(
    val id: Long,
    val memory: String,
    val image: Image
)

data class Image(
    val name: String,
    val isPlayable: Boolean,
    val id: Long,
    val mementoId: Long
)