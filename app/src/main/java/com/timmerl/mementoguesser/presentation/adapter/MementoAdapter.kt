package com.timmerl.mementoguesser.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.model.Question


/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoAdapter :
    RecyclerView.Adapter<ItemListViewHolder>() {
    private var selectionTracker: SelectionTracker<Question>? = null
    private val itemList = mutableListOf<Question>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.memento_item, parent, false)
        return ItemListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item, selectionTracker?.isSelected(item) ?: false)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun submitList(newList: List<Question>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    fun setSelectionTracker(selectionTracker: SelectionTracker<Question>) {
        this.selectionTracker = selectionTracker
    }

}

class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ViewHolderWithDetails {
    private lateinit var item: Question
    private var question: TextView = itemView.findViewById(R.id.mementoItemTextView)

    fun bind(item: Question, isActive: Boolean) {
        this.item = item
        Log.e("bind", "select(${item.question} = $isActive")
        itemView.isSelected = isActive
        question.text = item.question
    }

    override val itemDetails: ItemDetails<Question>
        get() = MyItemDetail(adapterPosition, item)

}


internal interface ViewHolderWithDetails {
    val itemDetails: ItemDetails<Question>
}

internal class MyItemDetail(private val adapterPosition: Int, private val selectionKey: Question) :
    ItemDetails<Question>() {
    override fun getPosition(): Int {
        return adapterPosition
    }

    @Nullable
    override fun getSelectionKey(): Question {
        return selectionKey
    }

    override fun inSelectionHotspot(e: MotionEvent): Boolean {
        return true
    }
}

internal class ActionModeController(
    private val context: Context,
    private val selectionTracker: SelectionTracker<Question>
) : ActionMode.Callback {

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return false
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        selectionTracker.clearSelection()
    }

}

internal class MementoKeyProvider(scope: Int, private val itemList: List<Question>) :
    ItemKeyProvider<Question>(scope) {
    override fun getKey(position: Int): Question {
        return itemList[position]
    }

    override fun getPosition(key: Question): Int {
        return itemList.indexOf(key)
    }
}

internal class MementoDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Question>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Question>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder = recyclerView.getChildViewHolder(view)
            if (viewHolder is ItemListViewHolder) {
                return viewHolder.itemDetails
            }
        }
        return null
    }

}
