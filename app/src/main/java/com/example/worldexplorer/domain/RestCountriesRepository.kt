package com.example.worldexplorer.domain

import com.example.worldexplorer.data.database.entities.CountryBasicEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel

//Cada repositorio
interface RestCountriesRepository {

    /** Inicializamos los datos de la app en Room */
    suspend fun initWorldExplorerDatabase(): Boolean

    /** Country Screen operations */
    suspend fun getCountryBasic(): List<CountryBasicModel>

    suspend fun getAllCountriesBasicOrderDesc(): List<CountryBasicModel>

    suspend fun getAllCountriesBasicOrderAsc(): List<CountryBasicModel>

    /** Detail Country Screen operations */
    suspend fun getDetailCountries(cca2: String): CountryDetailModel

    /** Quiz Screen operations
    suspend fun getCountriesByRegion(region: String): List<CountryBasicModel>

    /** Travek Screen operations */
    suspend fun getRandomCountryNameCca2(): Pair<String,String> */
}