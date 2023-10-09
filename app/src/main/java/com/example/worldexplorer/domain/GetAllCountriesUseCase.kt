package com.example.worldexplorer.domain

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.data.database.entities.toDatabase
import com.example.worldexplorer.domain.model.CountryItem
import javax.inject.Inject

class GetAllCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke() : List<CountryItem> {
        val countries = repository.getAllCountriesListFromApi("name,cca2")

        return if(countries.isNotEmpty()){
            repository.clearCountries()
            repository.insertCountries(countries.map { it.toDatabase() })
            countries
        } else {
            repository.getAllCountriesListFromDatabase()
        }
    }
}