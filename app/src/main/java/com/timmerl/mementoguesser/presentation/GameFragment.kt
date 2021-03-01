package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.map
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.GameFragment.State.NewQuestion
import com.timmerl.mementoguesser.presentation.GameFragment.State.ShowAnswer
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment(), View.OnClickListener {

    private val viewModel: GameViewModel by viewModel()
    private lateinit var questionTextView: TextView
    private lateinit var answerTextView: TextView
    private lateinit var countTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false).apply {
        questionTextView = findViewById(R.id.questionTextView)
        answerTextView = findViewById(R.id.answerTextView)
        countTextView = findViewById(R.id.countTextView)
        findViewById<ConstraintLayout>(R.id.gameFragmentContent).setOnClickListener(this@GameFragment)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { button ->
            findNavController()
                .navigate(R.id.action_GameFragment_to_AddQuestionFragment)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.randomQuestion.map { it.count }.observe(viewLifecycleOwner) { count ->
            countTextView.text = count
        }
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

    private var state: State = ShowAnswer
    override fun onClick(v: View?) {
        when (state) {
            ShowAnswer -> viewModel.getAnswer()
            NewQuestion -> viewModel.getRandomQuestion()
        }
        state = state.getNext()
    }

}