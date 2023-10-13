package com.example.worldexplorer.domain.usecases.detailcountries

import android.util.Log
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.detailcountries.DetailCountriesModel
import java.text.Normalizer
import javax.inject.Inject

class GetDetailCountriesUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {
    suspend operator fun invoke(cca2: String): DetailCountriesModel? {
        return repository.getDetailCountries(cca2)
    }
}