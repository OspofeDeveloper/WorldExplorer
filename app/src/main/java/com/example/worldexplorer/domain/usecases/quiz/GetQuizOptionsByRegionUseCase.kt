package com.example.worldexplorer.domain.usecases.quiz

import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import javax.inject.Inject

class GetQuizOptionsByRegionUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(region: String): List<CountryBasicModel> =
        repository.getQuizOptionsByRegion(region)
}