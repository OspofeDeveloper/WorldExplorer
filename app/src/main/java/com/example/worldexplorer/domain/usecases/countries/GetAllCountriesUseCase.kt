package com.example.worldexplorer.domain.usecases.countries

import com.example.worldexplorer.data.RestCountriesRepositoryImpl
import com.example.worldexplorer.domain.models.countries.CountriesModel
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepositoryImpl
) {
    suspend operator fun invoke(fields: String): List<CountriesModel> =
        repository.getAllCountries(fields)
}