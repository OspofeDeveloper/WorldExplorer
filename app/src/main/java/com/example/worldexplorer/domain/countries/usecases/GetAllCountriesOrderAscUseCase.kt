package com.example.worldexplorer.domain.countries.usecases

import com.example.worldexplorer.data.countries.RestCountriesRepository
import com.example.worldexplorer.domain.countries.model.CountriesInfo
import javax.inject.Inject

class GetAllCountriesOrderAscUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(): List<CountriesInfo> = repository.getAllCountriesOrderAsc()
}