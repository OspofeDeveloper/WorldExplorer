package com.example.worldexplorer.domain.models.quiz

import com.example.worldexplorer.R

/*
    If we have limited and fixed objects it is better to create a sealed class than a data class
    for an easier control of its logic because when we call one of this objects we do not have to
    specify the name and image, the object already knows what is its name and image.

    A data object is the same as an object with the difference that when debugging, we can see
    its actual value instead of an id
*/
sealed class QuizModel(val name: Int, val image: Int) {
    data object Europe : QuizModel(R.string.europe, R.drawable.europe)
    data object Africa : QuizModel(R.string.africa, R.drawable.africa)
    data object Asia : QuizModel(R.string.asia, R.drawable.asia)
    data object America : QuizModel(R.string.america, R.drawable.america)
    data object Oceania : QuizModel(R.string.oceania, R.drawable.oceania)
    data object Earth : QuizModel(R.string.earth, R.drawable.earth)

    /**
    data object WesternAsia : QuizModel(R.string.western_asia, R.drawable.western_asia)
    data object NorthernEurope : QuizModel(R.string.northern_europe, R.drawable.northern_europe)
    data object NorthernAfrica : QuizModel(R.string.northern_africa, R.drawable.northern_africa)
    data object EasternAfrica : QuizModel(R.string.eastern_africa, R.drawable.eastern_africa)
    data object Caribbean : QuizModel(R.string.caribbean, R.drawable.caribbean)
    data object SouthAmerica : QuizModel(R.string.south_america, R.drawable.south_america)*/
}
