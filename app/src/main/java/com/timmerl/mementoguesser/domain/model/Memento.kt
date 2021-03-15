package com.timmerl.mementoguesser.domain.model

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class Memento(
    val id: Long,
    val question: String,
    val answers: List<Answer>
)

data class Answer(
    val answer: String,
    val isPlayable: Boolean,
    val id: Long,
    val mementoId: Long
)