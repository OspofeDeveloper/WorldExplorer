package com.example.worldexplorer.domain.models.detailquiz

data class QuizDetailModel(
    val cca2Correct: String,
    val imageUrl: String,
    val options: List<QuizOptionModel>
)
