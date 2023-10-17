package com.example.worldexplorer.ui.quiz.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.example.worldexplorer.databinding.ItemQuizTopicBinding
import com.example.worldexplorer.domain.models.quiz.QuizModel

class QuizViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemQuizTopicBinding.bind(view)

    fun render(quizInfo: QuizModel, onItemSelected: (QuizModel) -> Unit) {
        //Hay que recordar que los elementos de las vistas tienen contexto
        val context = binding.tvQuizItem.context

        binding.ivQuizItem.setImageResource(quizInfo.image)
        binding.tvQuizItem.text = context.getString(quizInfo.name)

        binding.parent.setOnClickListener {
            startRotationAnimation(binding.ivQuizItem, newLambda = { onItemSelected(quizInfo) })
        }
    }

    fun startRotationAnimation(view: View, newLambda: () -> Unit) {
        view.animate().apply {
            duration = 500
            interpolator =
                LinearInterpolator() //Velocidad de la animacion a lo largo del tiempo, ahora va siempre igual de rapido
            rotationBy(360f) //Rota sobre su propio eje 360 grados
            withEndAction { newLambda() } //El codigo de la lambda se ejecuta al acabar la animación
            start()
        }
    }
}