package com.example.worldexplorer.ui.detailquiz

sealed class QuizDetailState {
    data object Loading:QuizDetailState()
    data class Error(val error: String):QuizDetailState()
    data class Success(val quizOptions: Pair<String, List<String>>):QuizDetailState()
}