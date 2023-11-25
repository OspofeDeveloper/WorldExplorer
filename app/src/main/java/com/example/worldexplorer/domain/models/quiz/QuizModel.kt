package com.example.worldexplorer.domain.models.quiz

import com.example.worldexplorer.R

sealed class QuizModel(val name: Int, val image: Int) {
    data object Europe : QuizModel(R.string.europe, R.drawable.europe)
    data object Africa : QuizModel(R.string.africa, R.drawable.africa)
    data object Asia : QuizModel(R.string.asia, R.drawable.asia)
    data object Americas : QuizModel(R.string.america, R.drawable.america)
    data object Oceania : QuizModel(R.string.oceania, R.drawable.oceania)
    data object Earth : QuizModel(R.string.earth, R.drawable.earth)
}
