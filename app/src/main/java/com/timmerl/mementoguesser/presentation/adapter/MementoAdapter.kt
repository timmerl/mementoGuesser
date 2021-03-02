package com.timmerl.mementoguesser.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timmerl.mementoguesser.R
import com.timmerl.mementoguesser.domain.model.Question

/**
 * Created by Timmerman_Lyderic on 02/03/2021.
 */

class MementoAdapter(private val onClick: (Question) -> Unit) :
    ListAdapter<Question, MementoAdapter.QuestionViewHolder>(QuestionDiffCallback) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class QuestionViewHolder(itemView: View, val onClick: (Question) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.mementoItemTextView)
        private var currentQuestion: Question? = null

        init {
            itemView.setOnClickListener {
                currentQuestion?.let {
                    onClick(it)
                }
            }
        }

        fun bind(question: Question) {
            currentQuestion = question

            questionTextView.text = question.question
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.memento_item, parent, false)
        return QuestionViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object QuestionDiffCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.id == newItem.id
    }
}
