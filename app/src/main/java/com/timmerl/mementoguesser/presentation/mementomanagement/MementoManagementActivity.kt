package com.timmerl.mementoguesser.presentation.mementomanagement

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.adapter.MementoAdapter
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memento_management_activity)

        val questionAdapter = MementoAdapter { Log.d("question", "click on $it") }
        findViewById<RecyclerView>(R.id.mementoManagementList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter
        }
        viewModel.questionList.observe(this, {
            it?.let {
                questionAdapter.submitList(it)
            }
        })
    }
}