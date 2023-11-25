package com.example.worldexplorer.domain.usecases.quiz

import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import javax.inject.Inject

class GetQuizOptionsByRegionUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {

    suspend operator fun invoke(correctCca2List: List<String>, region: String): QuizDetailModel =
        repository.getQuizOptionsByRegion(correctCca2List, region)

}