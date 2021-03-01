package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.utils.hideKeyboard
import com.timmerl.mementoguesser.presentation.utils.showAlertWithEditText

class MainActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { _ -> askForQuestion() }
    }

    override fun onStop() {
        super.onStop()
        hideKeyboard(currentFocus ?: View(this))
    }

    private fun askForQuestion() {
        showAlertWithEditText(
            title = "Donne ta question",
            positiveButtonText = "suivant",
            action = { question -> askForAnswer(question) }
        )
    }

    private fun askForAnswer(question: String) {
        showAlertWithEditText(
            title = "Et la réponse",
            positiveButtonText = "suivant",
            action = { answer -> saveQuestion(question = question, answer = answer) }
        )
    }

    private fun saveQuestion(question: String, answer: String) {
        viewModel.createQuestion(question, answer)
    }
}