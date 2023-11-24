package com.example.worldexplorer.domain.usecases.quiz

import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import javax.inject.Inject

class GetQuizOptionsGlobalUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(correctCca2List: List<String>): QuizDetailModel =
        repository.getQuizOptionsGlobal(correctCca2List)
}