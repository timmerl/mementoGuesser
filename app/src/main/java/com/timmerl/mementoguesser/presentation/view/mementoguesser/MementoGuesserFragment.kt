package com.timmerl.mementoguesser.presentation.view.mementoguesser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import org.koin.android.viewmodel.ext.android.viewModel

class MementoGuesserFragment : Fragment() {

    private val viewModel: MementoGuesserViewModel by viewModel()

    @OptIn(ExperimentalAnimatedInsets::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val windowInsets = ViewWindowInsetObserver(this)
            .start(windowInsetsAnimationsEnabled = true)

        setContent {
            CompositionLocalProvider(LocalWindowInsets provides windowInsets) {
                MgTheme {
                    MementoGuesserScreen(viewModel)
                }
            }
        }
    }
}