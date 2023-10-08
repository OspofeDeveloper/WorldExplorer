package com.example.worldexplorer.data

import com.example.worldexplorer.data.model.CountryItemModel
import com.example.worldexplorer.data.network.RestCountriesService
import com.example.worldexplorer.domain.model.CountryItem
import com.example.worldexplorer.domain.model.toDomain
import javax.inject.Inject

class RestCountriesRepository @Inject constructor(
    private val apiService: RestCountriesService
) {
    suspend fun getAllCountriesFromApi(): List<CountryItem> {
        val response: List<CountryItemModel> = apiService.getAllCountries()
        return response.map { it.toDomain() }
    }
}