package com.example.worldexplorer.domain.usecases.countries

import com.example.worldexplorer.data.RestCountriesRepositoryImpl
import com.example.worldexplorer.domain.models.countries.CountriesModel
import javax.inject.Inject

class GetAllCountriesOrderAscUseCase @Inject constructor(
    private val repository: RestCountriesRepositoryImpl
) {
    suspend operator fun invoke(): List<CountriesModel> = repository.getAllCountriesOrderAsc()
}