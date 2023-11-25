package com.example.worldexplorer.ui.quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.R
import com.example.worldexplorer.domain.models.quiz.QuizModel

class QuizAdapter(
    private var quizList: List<QuizModel> = emptyList(),
    private val onItemSelected: (QuizModel) -> Unit) :
    RecyclerView.Adapter<QuizViewHolder>() {

    fun updateList(list:List<QuizModel>) {
        quizList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        return QuizViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_quiz_topic, parent, false)
        )
    }

    override fun getItemCount(): Int = quizList.size

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        holder.render(quizList[position], onItemSelected)
    }

}