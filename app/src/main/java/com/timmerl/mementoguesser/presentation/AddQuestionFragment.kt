package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddQuestionFragment : Fragment() {

    private val viewModel: AddQuestionViewModel by viewModel()
    private lateinit var questionEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var answerImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionEditText = view.findViewById(R.id.questionEditText)
        answerEditText = view.findViewById(R.id.answerEditText)
        answerImage = view.findViewById(R.id.answerImage)

        view.findViewById<Button>(R.id.seaarchImageButton).setOnClickListener {
            viewModel.findImage(answerEditText.text.toString())
        }

        view.findViewById<FloatingActionButton>(R.id.saveAndQuitButton).setOnClickListener {
            if (saveQuestion())
                exit()
        }

        view.findViewById<FloatingActionButton>(R.id.saveAndContinueButton).setOnClickListener {
            if (saveQuestion())
                resetView()
        }

        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            // todo load image
        }
    }

    private fun saveQuestion(): Boolean {
        val question = questionEditText.text.toString()
        val answer = answerEditText.text.toString()

        viewModel.createQuestion(question, answer)
        return true
    }

    private fun resetView() {
        viewModel.reset()
        questionEditText.setText("")
        answerEditText.setText("")
        // todo hide image
    }

    private fun exit() {
        findNavController().navigate(R.id.action_AddQuestionFragment_to_GameFragment)
    }

}