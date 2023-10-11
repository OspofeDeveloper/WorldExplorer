package com.example.worldexplorer.data.countries.network

import com.example.worldexplorer.data.countries.model.CountryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestCountriesService @Inject constructor(
    private val api : RestCountriesApiClient
) {
    suspend fun getAllCountriesList(fields: String): List<CountryModel> {
        return try {
            withContext(Dispatchers.IO) {
                val response = api.getAllCountries(fields)
                response
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}