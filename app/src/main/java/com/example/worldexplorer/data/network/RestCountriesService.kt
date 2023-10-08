package com.example.worldexplorer.data.network

import com.example.worldexplorer.data.model.CountryItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestCountriesService @Inject constructor(
    private val api : RestCountriesApiClient
) {
    suspend fun getAllCountries() : List<CountryItemModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllCountries()
            response ?: emptyList()
        }
    }
}