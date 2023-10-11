package com.example.worldexplorer.data.countries

import com.example.worldexplorer.data.countries.database.dao.CountryDao
import com.example.worldexplorer.data.countries.database.entities.CountryEntity
import com.example.worldexplorer.data.countries.database.entities.toDatabase
import com.example.worldexplorer.data.countries.network.RestCountriesService
import com.example.worldexplorer.domain.countries.model.CountriesInfo
import com.example.worldexplorer.domain.countries.model.toDomain
import javax.inject.Inject

class RestCountriesRepository @Inject constructor(
    private val apiService: RestCountriesService,
    private val countryItemDao: CountryDao
) {
    suspend fun getAllCountries(fields: String): List<CountriesInfo> {
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

    suspend fun getAllCountriesOrderAsc(): List<CountriesInfo> {
        return countryItemDao.getAllCountriesOrderAsc().map { it.toDomain() }
    }

    suspend fun getAllCountriesOrderDesc(): List<CountriesInfo> {
        return countryItemDao.getAllCountriesOrderDesc().map { it.toDomain() }
    }
}