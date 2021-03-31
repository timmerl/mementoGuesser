package com.timmerl.mementoguesser.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.timmerl.mementoguesser.presentation.utils.LocalSysUiController
import com.timmerl.mementoguesser.presentation.utils.SystemUiController
import com.timmerl.mementoguesser.presentation.view.app.MgApp

class MainActivity : AppCompatActivity() {

    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {
                ProvideWindowInsets {
                    Box(modifier = Modifier.imePadding()) {
                        MgApp(onBackPressedDispatcher)
                    }
                }
            }
        }
    }
}