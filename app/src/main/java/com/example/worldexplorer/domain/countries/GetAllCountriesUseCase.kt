package com.example.worldexplorer.domain.countries

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.domain.countries.model.CountryItem
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(fields: String) : List<CountryItem> = repository.getAllCountries(fields)
}