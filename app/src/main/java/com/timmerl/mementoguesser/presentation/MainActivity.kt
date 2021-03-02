package com.timmerl.mementoguesser.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.presentation.adapter.QuestionListAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionAdapter = QuestionListAdapter { Log.d("question", "click on $it") }

        val recyclerView: RecyclerView = findViewById(R.id.questionList)
        recyclerView.adapter = questionAdapter
        viewModel.questionList.observe(this, {
            it?.let {
                questionAdapter.submitList(it as MutableList<Question>)
//                headerAdapter.updateFlowerCount(it.size)
            }
        })
    }

}