package com.example.worldexplorer.domain.listCountriesScreen

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.domain.listCountriesScreen.model.CountryItem
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(fields: String) : List<CountryItem> = repository.getAllCountries(fields)
}