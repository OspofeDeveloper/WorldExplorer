package com.example.worldexplorer.domain.countries

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.domain.countries.model.CountryItem
import javax.inject.Inject

class GetAllCountriesOrderAscUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(): List<CountryItem> = repository.getAllCountriesOrderAsc()
}