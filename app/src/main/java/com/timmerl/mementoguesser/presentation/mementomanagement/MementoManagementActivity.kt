package com.timmerl.mementoguesser.presentation.mementomanagement

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MementoManagementsScreen(viewModel)
        }
    }
}