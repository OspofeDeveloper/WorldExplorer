package com.example.worldexplorer.domain.countries.usecases

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.domain.countries.model.CountriesInfo
import javax.inject.Inject

class GetAllCountriesOrderDescUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {

    suspend operator fun invoke(): List<CountriesInfo> = repository.getAllCountriesOrderDesc()
}