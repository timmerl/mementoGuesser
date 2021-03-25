package com.timmerl.mementoguesser.presentation.model


/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class QuestionUiModel(
    val mementoId: Long,
    val imageId: Long,
    val memory: String,
    val image: String,
    val isPlayable: Boolean
)