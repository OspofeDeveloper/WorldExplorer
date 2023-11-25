package com.example.worldexplorer.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.worldexplorer.data.providers.QuizProvider
import com.example.worldexplorer.domain.models.quiz.QuizModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(quizProvider: QuizProvider) : ViewModel() {

    private var _quiz = MutableStateFlow<List<QuizModel>>(emptyList())
    val quiz: StateFlow<List<QuizModel>> = _quiz

    init {
        _quiz.value = quizProvider.getQuizItems()
    }

}