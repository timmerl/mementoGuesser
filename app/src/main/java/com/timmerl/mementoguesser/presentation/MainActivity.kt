package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.utils.showAlertWithEditText

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ -> askForQuestion() }
    }

    private fun askForQuestion() {
        showAlertWithEditText(
            title = "Donne ta question",
            positiveButtonText = "suivant",
            action = { question -> askForAnswer(question) },
            showKeyBoardOnAppear = true,
            hideKeyBoardOnDismiss = false
        )
    }

    private fun askForAnswer(question: String) {
        showAlertWithEditText(
            title = "Et la réponse",
            positiveButtonText = "suivant",
            action = { answer -> saveQuestion(question = question, answer = answer) },
            showKeyBoardOnAppear = false,
            hideKeyBoardOnDismiss = true,
        )
    }

    private fun saveQuestion(question: String, answer: String) {
        viewModel.createQuestion(question, answer)
    }
}