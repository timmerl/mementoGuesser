package com.timmerl.mementoguesser.presentation.addquestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.timmerl.mementoguesser.presentation.composable.AddMementoScreen
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddMementoFragment : Fragment() {

    private val viewModel: AddMementoViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            viewModel.uiState.observeAsState().value?.getEventIfNotHandled().let {
                if (it is AddMementoAction.NewMemento) {
                    AddMementoScreen { memory, image ->
                        viewModel.createMemento(
                            memory = memory,
                            image = image
                        )
                    }
                }
            }
        }
    }
}