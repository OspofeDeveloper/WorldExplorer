package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.model.CountryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestCountriesService @Inject constructor(
    private val api : RestCountriesApiClient
) {
    suspend fun getAllCountriesList(fields: String): List<CountryModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllCountries(fields)
            response ?: emptyList()
        }
    }
}