package com.timmerl.mementoguesser.presentation.mementomanagement

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.composable.MementoManagementsScreen
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memento_management)

        setContent {
            MementoManagementsScreen(this, viewModel)
        }

    }
}