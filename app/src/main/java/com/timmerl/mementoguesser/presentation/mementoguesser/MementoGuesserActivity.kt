package com.timmerl.mementoguesser.presentation.mementoguesser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.timmerl.mementoguesser.presentation.utils.observeEvent
import org.koin.android.viewmodel.ext.android.viewModel

class MementoGuesserActivity : AppCompatActivity() {

    private val viewModel: MementoGuesserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MementoGuesserScreen(viewModel = viewModel)
        }

        viewModel.navigationEvent.observeEvent(this) { event -> event.launch(this) }
    }

    companion object {
        fun launch(context: Context) {
            ContextCompat.startActivity(
                context,
                Intent(context, MementoGuesserActivity::class.java),
                null
            )
        }
    }

}