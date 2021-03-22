package com.timmerl.mementoguesser.presentation.mementomanagement

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.Text
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.presentation.composable.MementoMamanagementScreen
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoManagementActivity : AppCompatActivity() {
    private val viewModel: MementoManagementViewModel by viewModel()
//    private lateinit var selectionTracker: SelectionTracker<QuestionUiModel>
//    private lateinit var recyclerView: RecyclerView
//    private var actionMode: ActionMode? = null
//    private val questionAdapter = MementoAdapter()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memento_management)

        setContent {
//            Text("This is a text broh")
            MementoMamanagementScreen(viewModel)
        }


//        val llm = LinearLayoutManager(this)
//        recyclerView = findViewById<RecyclerView>(R.id.mementoManagementList).apply {
//            layoutManager = llm
//            adapter = questionAdapter
//            addItemDecoration(DividerItemDecoration(context, llm.orientation))
//        }
//        findViewById<FloatingActionButton>(R.id.mementoManagementListToggleIsPlayable).apply {
//            setOnSafeClickListener { view ->
//                val itemIterable = selectionTracker.selection.iterator()
//                while (itemIterable.hasNext()) {
//                    viewModel.toggleIsPlayable(itemIterable.next())
//                }
//                selectionTracker.clearSelection()
//            }
//        }
//        findViewById<FloatingActionButton>(R.id.mementoManagementListRemoveSelection).apply {
//            setOnSafeClickListener { view ->
//                val itemIterable = selectionTracker.selection.iterator()
//                while (itemIterable.hasNext()) {
//                    viewModel.remove(itemIterable.next())
//                }
//                selectionTracker.clearSelection()
//            }
//        }
//        if (savedInstanceState != null) {
//            selectionTracker.onRestoreInstanceState(savedInstanceState);
//        }
    }

//    override fun onResume() {
//        super.onResume()
//        viewModel.questionList.observe(this, { newQuestions ->
//            newQuestions?.let {
//                questionAdapter.submitList(it)
//            }
//            selectionTracker = SelectionTracker.Builder(
//                "selectionId",
//                recyclerView,
//                MementoKeyProvider(1, newQuestions),
//                MementoDetailsLookup(recyclerView),
//                StorageStrategy.createParcelableStorage(QuestionUiModel::class.java)
//            ).build()
//            questionAdapter.setSelectionTracker(selectionTracker)
//            selectionTracker.addObserver(object :
//                SelectionTracker.SelectionObserver<QuestionUiModel>() {
//                override fun onSelectionChanged() {
//                    super.onSelectionChanged()
//                    if (selectionTracker.hasSelection() && actionMode == null) {
//                        actionMode = startSupportActionMode(ActionModeController(selectionTracker))
//                    } else if (!selectionTracker.hasSelection() && actionMode != null) {
//                        actionMode?.finish()
//                        actionMode = null
//                    }
//                }
//            })
//        })
//    }
//
//    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        selectionTracker.onSaveInstanceState(outState)
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        startActivity(Intent(this, MementoGuesserActivity::class.java))
//    }
}