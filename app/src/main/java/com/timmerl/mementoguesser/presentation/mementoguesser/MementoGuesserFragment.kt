package com.timmerl.mementoguesser.presentation.mementoguesser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MementoGuesserFragment : Fragment() {

    private val viewModel: MementoGuesserViewModel by viewModel()
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var mementoCountTextView: TextView
    private lateinit var sortButton: Button
    private lateinit var switchQaButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_memento_guesser, container, false).apply {
        questionTextView = findViewById(R.id.mementoGuesserFragment_questionTextView)
        answerTextView = findViewById(R.id.mementoGuesserFragment_answerTextView)
        mementoCountTextView = findViewById(R.id.mementoGuesserFragment_countTextView)
        findViewById<ConstraintLayout>(R.id.mementoGuesserFragment_gameContent).setOnClickListener {
            viewModel.continueGame()
        }
        findViewById<FloatingActionButton>(R.id.navigateAddMementoFab)
            .setOnClickListener {
                findNavController().navigate(R.id.action_GameFragment_to_AddQuestionFragment)
            }
        sortButton = findViewById<Button>(R.id.mementoGuesserFragment_sortButton).apply {
            setOnClickListener {
                viewModel.toggleSorting()
            }
        }
        switchQaButton = findViewById<Button>(R.id.mementoGuesserFragment_switchQAButton).apply {
            setOnClickListener {
                viewModel.toggleQA()
            }
        }

        viewModel.startGame()
    }

    override fun onResume() {
        super.onResume()
        viewModel.memento.map { it.count }.observe(viewLifecycleOwner) { count ->
            mementoCountTextView.text = count
        }
        viewModel.memento.map { it.question }.observe(viewLifecycleOwner) { question ->
            if (question == null) {
                questionTextView.visibility = TextView.INVISIBLE
            } else {
                questionTextView.text = question
                questionTextView.visibility = TextView.VISIBLE
            }
        }
        viewModel.memento.map { it.answer }.observe(viewLifecycleOwner) { answer ->
            if (answer == null) {
                answerTextView.visibility = TextView.INVISIBLE
            } else {
                answerTextView.text = answer
                answerTextView.visibility = TextView.VISIBLE
            }
        }
        viewModel.memento.map { it.sortButtonText }.observe(viewLifecycleOwner) { text ->
            if (text == 0) {
                sortButton.visibility = GONE
            } else {
                sortButton.visibility = VISIBLE
                sortButton.text = getString(text)
            }
        }
        viewModel.memento.map { it.switchQAButtonText }.observe(viewLifecycleOwner) { text ->
            if (text == 0) {
                switchQaButton.visibility = GONE
            } else {
                switchQaButton.visibility = VISIBLE
                switchQaButton.text = getString(text)
            }
        }
    }

    interface IState {
        fun getNext(): State
    }

    sealed class State : IState {
        object ShowAnswer : State(), IState {
            override fun getNext(): State = NewQuestion
        }

        object NewQuestion : State(), IState {
            override fun getNext(): State = ShowAnswer
        }
    }
}