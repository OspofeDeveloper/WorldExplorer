package com.example.worldexplorer.domain

import com.example.worldexplorer.data.database.entities.CountriesEntity
import com.example.worldexplorer.domain.models.countries.CountriesModel

//Cada repositorio
interface RestCountriesRepository {
    suspend fun getAllCountriesOrderDesc(): List<CountriesModel>
    suspend fun getAllCountriesOrderAsc(): List<CountriesModel>
    suspend fun clearCountries()
    suspend fun insertCountries(countries:List<CountriesEntity>)
    suspend fun getAllCountries(fields: String): List<CountriesModel>
}