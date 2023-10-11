package com.example.worldexplorer.ui.countries

import com.example.worldexplorer.domain.models.countries.CountriesModel
import com.example.worldexplorer.ui.detailcountries.CountriesDetailState

sealed class CountriesState {
    data object Loading: CountriesState()
    data class Error(val error: String): CountriesState()
    data class Success(val countries: List<CountriesModel>): CountriesState()
}