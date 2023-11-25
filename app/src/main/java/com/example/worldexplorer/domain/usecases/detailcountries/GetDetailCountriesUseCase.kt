package com.example.worldexplorer.domain.usecases.detailcountries

import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import javax.inject.Inject

class GetDetailCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {

    suspend operator fun invoke(cca2: String): CountryDetailModel {
        return repository.getDetailCountries(cca2)
    }

}