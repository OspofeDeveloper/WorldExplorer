package com.example.worldexplorer.data.providers

import com.example.worldexplorer.domain.models.quiz.QuizModel
import com.example.worldexplorer.domain.models.quiz.QuizModel.Africa
import com.example.worldexplorer.domain.models.quiz.QuizModel.America
import com.example.worldexplorer.domain.models.quiz.QuizModel.Asia
import com.example.worldexplorer.domain.models.quiz.QuizModel.Earth
import com.example.worldexplorer.domain.models.quiz.QuizModel.Europe
import com.example.worldexplorer.domain.models.quiz.QuizModel.Oceania
import javax.inject.Inject

class QuizProvider @Inject constructor() {
    fun getQuizItems(): List<QuizModel> {
        return listOf(
            Earth,
            Europe,
            Africa,
            Asia,
            America,
            Oceania
        )
    }

    /**
    Caribbean,
    EasternAfrica,
    NorthernAfrica,
    NorthernEurope,
    SouthAmerica,
    WesternAsia
     */
}