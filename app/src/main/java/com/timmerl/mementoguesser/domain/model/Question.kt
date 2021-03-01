package com.timmerl.mementoguesser.domain.model

/**
 * Created by Timmerman_Lyderic on 28/02/2021.
 */

data class Question(
    val id: Int,
    val question: String,
    val answer: String,
    val url: String?
)