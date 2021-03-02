package com.timmerl.mementoguesser.presentation.mementomanagement

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.model.Question
import com.timmerl.mementoguesser.presentation.adapter.ActionModeController
import com.timmerl.mementoguesser.presentation.adapter.MementoAdapter
import com.timmerl.mementoguesser.presentation.adapter.MementoDetailsLookup
import com.timmerl.mementoguesser.presentation.adapter.MementoKeyProvider
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()
    private lateinit var selectionTracker: SelectionTracker<Question>
    private lateinit var recyclerView: RecyclerView
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.memento_management_activity)

        val questionAdapter = MementoAdapter()
        val llm = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.mementoManagementList).apply {
            layoutManager = llm
            adapter = questionAdapter
        }
        viewModel.questionList.observe(this, { newQuestions ->
            newQuestions?.let {
                questionAdapter.submitList(it)
            }
            Log.e("questionList", "content is updated")
            selectionTracker = SelectionTracker.Builder(
                "selectionId",
                recyclerView,
                MementoKeyProvider(1, newQuestions),
                MementoDetailsLookup(recyclerView),
                StorageStrategy.createParcelableStorage(Question::class.java)
            ).withOnContextClickListener { event ->
                Log.e(
                    "questionList",
                    "onContextClick"
                )
                false
            }.withOnItemActivatedListener { item, event ->
                Log.e(
                    "questionList",
                    "onItemActivated : ${item.selectionKey?.question ?: "errror"}"
                )
                true
            }.withOnDragInitiatedListener { event ->
                Log.e("questionList", "onDragInitiated")
                true
            }.build()
            questionAdapter.setSelectionTracker(selectionTracker)
            selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Question>() {

                override fun onItemStateChanged(key: Question, selected: Boolean) {
                    super.onItemStateChanged(key, selected)
                }

                override fun onSelectionRefresh() {
                    super.onSelectionRefresh()
                }

                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    Log.e(
                        "onSelectionChanged",
                        "something happen"
                    )
                    if (selectionTracker.hasSelection() && actionMode == null) {
                        actionMode = startSupportActionMode(
                            ActionModeController(
                                this@MementoManagementActivity,
                                selectionTracker
                            )
                        )
                        Log.e(
                            "onSelectionChanged",
                            "new selection is ${selectionTracker.selection}"
                        )
                    } else if (!selectionTracker.hasSelection() && actionMode != null) {
                        actionMode?.finish()
                        actionMode = null
                    }
                    val itemIterable = selectionTracker.selection.iterator()
                    while (itemIterable.hasNext()) {
                        Log.e("onSelectionChanged", itemIterable.next().question)
                    }

                }
            })

        })
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        selectionTracker.onSaveInstanceState(outState)
    }


}