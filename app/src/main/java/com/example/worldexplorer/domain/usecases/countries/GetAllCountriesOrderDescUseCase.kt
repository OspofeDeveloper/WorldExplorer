package com.example.worldexplorer.domain.usecases.countries

import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountriesModel
import javax.inject.Inject

class GetAllCountriesOrderDescUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {

    suspend operator fun invoke(): List<CountriesModel> = repository.getAllCountriesOrderDesc()
}