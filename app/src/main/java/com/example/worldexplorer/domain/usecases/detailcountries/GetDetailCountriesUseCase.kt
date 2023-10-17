package com.example.worldexplorer.domain.usecases.detailcountries

import android.util.Log
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountriesModel
import javax.inject.Inject

class GetDetailCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(cca2: String): CountriesModel? {
        return repository.getDetailCountries(cca2)
    }
}