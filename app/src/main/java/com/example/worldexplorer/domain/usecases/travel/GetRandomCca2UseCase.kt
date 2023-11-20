package com.example.worldexplorer.domain.usecases.travel

import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import javax.inject.Inject

class GetRandomCca2UseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(): CountryBasicModel = repository.getRandomCountryNameCca2()
}