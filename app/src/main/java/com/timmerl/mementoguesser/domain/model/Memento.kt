package com.timmerl.mementoguesser.domain.model

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class Memento(
    val id: Int,
    val question: String,
    val answers: List<String>,
    val isPlayable: Boolean
)