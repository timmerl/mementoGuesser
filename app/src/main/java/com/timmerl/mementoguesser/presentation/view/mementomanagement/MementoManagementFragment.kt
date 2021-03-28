package com.timmerl.mementoguesser.presentation.view.mementomanagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ViewWindowInsetObserver
import com.timmerl.mementoguesser.presentation.theme.MgTheme
import com.timmerl.mementoguesser.presentation.utils.LocalBackPressedDispatcher
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementFragment : Fragment() {
    private val viewModel: MementoManagementViewModel by viewModel()

    @ExperimentalFoundationApi
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
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides requireActivity().onBackPressedDispatcher,
                LocalWindowInsets provides windowInsets,
            ) {
                MgTheme {
                    MementoManagementsScreen(viewModel)
                }
            }
        }
    }
}