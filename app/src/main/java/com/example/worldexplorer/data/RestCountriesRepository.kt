package com.example.worldexplorer.data

import com.example.worldexplorer.data.database.dao.CountryDao
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.network.RestCountriesService
import com.example.worldexplorer.domain.model.CountryItem
import com.example.worldexplorer.domain.model.toDomain
import javax.inject.Inject

class RestCountriesRepository @Inject constructor(
    private val apiService: RestCountriesService,
    private val countryItemDao: CountryDao
) {
    suspend fun getAllCountriesListFromApi(fields: String): List<CountryItem> =
        apiService.getAllCountriesList(fields).map { it.toDomain() }

    suspend fun getAllCountriesListFromDatabase(): List<CountryItem> {
        val response: List<CountryEntity> = countryItemDao.getAllCountries()
        return response.map { it.toDomain() }
    }

    suspend fun insertCountries(countries:List<CountryEntity>) {
        countryItemDao.insertAll(countries)
    }

    suspend fun clearCountries() {
        countryItemDao.deleteAllCountries()
    }
}