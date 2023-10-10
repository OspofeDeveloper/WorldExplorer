package com.example.worldexplorer.ui.quiz.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.databinding.ItemQuizBinding
import com.example.worldexplorer.domain.model.QuizInfo

class QuizViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemQuizBinding.bind(view)

    fun render(quizInfo: QuizInfo) {
        //Hay que recordar que los elementos de las vistas tienen contexto
        val context = binding.tvQuizItem.context

        binding.ivQuizItem.setImageResource(quizInfo.image)
        binding.tvQuizItem.text = context.getString(quizInfo.name)
    }
}