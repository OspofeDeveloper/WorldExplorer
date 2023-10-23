package com.example.worldexplorer.ui.detailquiz

sealed class QuizDetailState {
    data object Loading:QuizDetailState()
    data class Error(val error: String):QuizDetailState()
    data class Success(val quizOptions: List<Pair<String, List<Pair<String, Boolean>>>>):QuizDetailState()
}