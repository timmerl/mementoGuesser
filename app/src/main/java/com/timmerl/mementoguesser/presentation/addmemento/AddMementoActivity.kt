package com.timmerl.mementoguesser.presentation.addmemento

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.content.ContextCompat.startActivity
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class AddMementoActivity : AppCompatActivity() {
    private val viewModel: AddMementoViewModel by viewModel()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddMementoScreen(viewModel)
        }
    }

    companion object {
        fun launch(context: Context) {
            startActivity(
                context,
                Intent(context, AddMementoActivity::class.java),
                null
            )
        }
    }
}