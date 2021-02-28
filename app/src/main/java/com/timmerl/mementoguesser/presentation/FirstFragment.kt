package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import com.timmerl.mementoguesser.R
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val viewModel: GameViewModel by sharedViewModel()
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var answerButton: Button
    private lateinit var nextButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_first, container, false).apply {
        questionTextView = findViewById(R.id.questionTextView)
        answerTextView = findViewById(R.id.answerTextView)
        answerButton = findViewById(R.id.answerButton)
        nextButton = findViewById(R.id.nextButton)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.randomQuestion.map { it.question }.observe(viewLifecycleOwner) { question ->
            if (question == null) {
                questionTextView.visibility = TextView.INVISIBLE
            } else {
                questionTextView.text = question
                questionTextView.visibility = TextView.VISIBLE
            }
        }
        viewModel.randomQuestion.map { it.answer }.observe(viewLifecycleOwner) { answer ->
            if (answer == null) {
                answerTextView.visibility = TextView.INVISIBLE
            } else {
                answerTextView.text = answer
                answerTextView.visibility = TextView.VISIBLE
            }
        }

        nextButton.setOnClickListener {
            viewModel.getRandomQuestion()
        }

        answerButton.setOnClickListener {
            viewModel.getAnswer()
        }
    }
}