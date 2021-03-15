package com.timmerl.mementoguesser.presentation.mementomanagement

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.adapter.ActionModeController
import com.timmerl.mementoguesser.presentation.adapter.MementoAdapter
import com.timmerl.mementoguesser.presentation.adapter.MementoDetailsLookup
import com.timmerl.mementoguesser.presentation.adapter.MementoKeyProvider
import com.timmerl.mementoguesser.presentation.mementoguesser.MementoGuesserActivity
import com.timmerl.mementoguesser.presentation.model.QuestionUiModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()
    private lateinit var selectionTracker: SelectionTracker<QuestionUiModel>
    private lateinit var recyclerView: RecyclerView
    private var actionMode: ActionMode? = null
    private val questionAdapter = MementoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memento_management)

        val llm = LinearLayoutManager(this)
        recyclerView = findViewById<RecyclerView>(R.id.mementoManagementList).apply {
            layoutManager = llm
            adapter = questionAdapter
            addItemDecoration(DividerItemDecoration(context, llm.orientation))
        }
        findViewById<FloatingActionButton>(R.id.mementoManagementListToggleIsPlayable).apply {
            setOnClickListener { view ->
                val itemIterable = selectionTracker.selection.iterator()
                while (itemIterable.hasNext()) {
                    viewModel.toggleIsPlayable(itemIterable.next())
                }
                selectionTracker.clearSelection()
            }
        }
        findViewById<FloatingActionButton>(R.id.mementoManagementListRemoveSelection).apply {
            setOnClickListener { view ->
                val itemIterable = selectionTracker.selection.iterator()
                while (itemIterable.hasNext()) {
                    viewModel.remove(itemIterable.next())
                }
                selectionTracker.clearSelection()
            }
        }
        if (savedInstanceState != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.questionList.observe(this, { newQuestions ->
            newQuestions?.let {
                questionAdapter.submitList(it)
            }
            selectionTracker = SelectionTracker.Builder(
                "selectionId",
                recyclerView,
                MementoKeyProvider(1, newQuestions),
                MementoDetailsLookup(recyclerView),
                StorageStrategy.createParcelableStorage(QuestionUiModel::class.java)
            ).build()
            questionAdapter.setSelectionTracker(selectionTracker)
            selectionTracker.addObserver(object :
                SelectionTracker.SelectionObserver<QuestionUiModel>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    if (selectionTracker.hasSelection() && actionMode == null) {
                        actionMode = startSupportActionMode(ActionModeController(selectionTracker))
                    } else if (!selectionTracker.hasSelection() && actionMode != null) {
                        actionMode?.finish()
                        actionMode = null
                    }
                }
            })
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        selectionTracker.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MementoGuesserActivity::class.java))
    }
}