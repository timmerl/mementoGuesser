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
class AddMementoFragment : Fragment() {

    private val viewModel: AddMementoViewModel by viewModel()
    private lateinit var memoryEditText: EditText
    private lateinit var imageEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_memento, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memoryEditText = view.findViewById(R.id.memoryEditText)
        imageEditText = view.findViewById(R.id.imageEditText)

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
        giveFocusToMemoryEditText()
    }

    private fun saveQuestion(): Boolean {
        val memory = memoryEditText.text.toString()
        val image = imageEditText.text.toString()

        viewModel.createMemento(memory, image)
        return true
    }

    private fun resetView() {
        memoryEditText.setText("")
        imageEditText.setText("")
        giveFocusToMemoryEditText()
    }

    private fun giveFocusToMemoryEditText() {
        memoryEditText.requestFocus()
        view?.showKeyboard()
    }

    private fun exit() {
        findNavController().navigate(R.id.action_AddQuestionFragment_to_GameFragment)
    }

}