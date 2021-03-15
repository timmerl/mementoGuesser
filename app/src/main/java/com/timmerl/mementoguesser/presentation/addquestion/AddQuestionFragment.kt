package com.timmerl.mementoguesser.presentation.addquestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.utils.setOnSafeClickListener
import com.timmerl.mementoguesser.presentation.utils.showKeyboard
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddQuestionFragment : Fragment() {

    private val viewModel: AddQuestionViewModel by viewModel()
    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionEditText = view.findViewById(R.id.questionEditText)
        answerEditText = view.findViewById(R.id.answerEditText)

        view.findViewById<FloatingActionButton>(R.id.saveAndQuitButton)
            .setOnSafeClickListener {
                if (saveQuestion())
                    exit()
            }

        view.findViewById<FloatingActionButton>(R.id.saveAndContinueButton)
            .setOnSafeClickListener {
                if (saveQuestion())
                    resetView()
            }
    }

    override fun onResume() {
        super.onResume()
        giveFocusToQuestionEditText()
    }

    private fun saveQuestion(): Boolean {
        val question = questionEditText.text.toString()
        val answer = answerEditText.text.toString()

        viewModel.createQuestion(question, answer)
        return true
    }

    private fun resetView() {
        questionEditText.setText("")
        answerEditText.setText("")
        giveFocusToQuestionEditText()
    }

    private fun giveFocusToQuestionEditText() {
        questionEditText.requestFocus()
        view?.showKeyboard()
    }

    private fun exit() {
        findNavController().navigate(R.id.action_AddQuestionFragment_to_GameFragment)
    }

}