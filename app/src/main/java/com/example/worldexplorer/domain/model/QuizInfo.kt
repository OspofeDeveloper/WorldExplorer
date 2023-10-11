package com.example.worldexplorer.domain.model

import com.example.worldexplorer.R

/*
    If we have limited and fixed objects it is better to create a sealed class than a data class
    for an easier control of its logic because when we call one of this objects we do not have to
    specify the name and image, the object already knows what is its name and image
*/
sealed class QuizInfo(val name: Int, val image: Int){
    data object Europe:QuizInfo(R.string.europe, R.drawable.europe)
    data object Africa:QuizInfo(R.string.africa, R.drawable.africa)
    data object Asia:QuizInfo(R.string.asia, R.drawable.asia)
    data object America:QuizInfo(R.string.america, R.drawable.america)
    data object Oceania:QuizInfo(R.string.oceania, R.drawable.oceania)
    data object WesternAsia:QuizInfo(R.string.western_asia, R.drawable.western_asia)
    data object NorthernEurope:QuizInfo(R.string.northern_europe, R.drawable.northern_europe)
    data object NorthernAfrica:QuizInfo(R.string.northern_africa, R.drawable.northern_africa)
    data object EasternAfrica:QuizInfo(R.string.eastern_africa, R.drawable.eastern_africa)
    data object Caribbean:QuizInfo(R.string.caribbean, R.drawable.caribbean)
    data object SouthAmerica:QuizInfo(R.string.south_america, R.drawable.south_america)
    data object Earth:QuizInfo(R.string.earth, R.drawable.earth)
}
