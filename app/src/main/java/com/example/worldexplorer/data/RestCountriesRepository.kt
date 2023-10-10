package com.example.worldexplorer.data

import com.example.worldexplorer.data.database.dao.CountryDao
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.toDatabase
import com.example.worldexplorer.data.network.RestCountriesService
import com.example.worldexplorer.domain.model.CountryItem
import com.example.worldexplorer.domain.model.toDomain
import javax.inject.Inject

class RestCountriesRepository @Inject constructor(
    private val apiService: RestCountriesService,
    private val countryItemDao: CountryDao
) {
    suspend fun getAllCountries(fields: String): List<CountryItem> {
        val response = apiService.getAllCountriesList(fields).map { it.toDomain() }

        return if(response.isNotEmpty()) {
            clearCountries()
            insertCountries(response.map { it.toDatabase() })
            response
        } else {
            countryItemDao.getAllCountries().map { it.toDomain() }
        }
    }

    suspend fun insertCountries(countries:List<CountryEntity>) {
        countryItemDao.insertAll(countries)
    }

    suspend fun clearCountries() {
        countryItemDao.deleteAllCountries()
    }

    suspend fun getAllCountriesOrderAsc(): List<CountryItem> {
        return countryItemDao.getAllCountriesOrderAsc().map { it.toDomain() }
    }

    suspend fun getAllCountriesOrderDesc(): List<CountryItem> {
        return countryItemDao.getAllCountriesOrderDesc().map { it.toDomain() }
    }
}