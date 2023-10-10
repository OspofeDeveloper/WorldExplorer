package com.example.worldexplorer.ui.quiz

import androidx.lifecycle.ViewModel
import com.example.worldexplorer.domain.model.QuizInfo
import com.example.worldexplorer.domain.model.QuizInfo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor() : ViewModel() {

    private var _quiz = MutableStateFlow<List<QuizInfo>>(emptyList())
    val quiz: StateFlow<List<QuizInfo>> = _quiz

    init {
        _quiz.value = listOf(
            Europe, Asia, Africa
        )
    }
}