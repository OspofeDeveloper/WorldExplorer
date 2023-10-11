package com.example.worldexplorer.data.providers

import com.example.worldexplorer.domain.quiz.model.QuizInfo
import com.example.worldexplorer.domain.quiz.model.QuizInfo.*
import javax.inject.Inject

class QuizProvider @Inject constructor(){
    fun getQuizItems(): List<QuizInfo> {
        return listOf(
            Earth,
            Europe,
            Africa,
            Asia,
            America,
            Oceania,
            Caribbean,
            EasternAfrica,
            NorthernAfrica,
            NorthernEurope,
            SouthAmerica,
            WesternAsia
        )
    }
}