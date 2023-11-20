package com.example.worldexplorer.domain

import com.example.worldexplorer.data.database.entities.CountryBasicEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel

//Cada repositorio
interface RestCountriesRepository {

    /** Inicializamos los datos de la app en Room */
    suspend fun initWorldExplorerDatabase(): Boolean

    /** Country Screen functions */
    suspend fun getCountryBasic(): List<CountryBasicModel>
    suspend fun getAllCountriesBasicOrderDesc(): List<CountryBasicModel>
    suspend fun getAllCountriesBasicOrderAsc(): List<CountryBasicModel>

    /** Detail Country Screen functions */
    suspend fun getDetailCountries(cca2: String): CountryDetailModel

    /** Quiz Screen operations */
    suspend fun getQuizOptionsGlobal(correctCca2List: List<String>) : QuizDetailModel
    suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String): QuizDetailModel
    suspend fun convertToQuizDetailModel(result: List<CountryBasicEntity>): QuizDetailModel

    /** Travek Screen operations
    suspend fun getRandomCountryNameCca2(): Pair<String,String>*/
}