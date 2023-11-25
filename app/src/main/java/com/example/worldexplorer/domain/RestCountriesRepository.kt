package com.example.worldexplorer.domain

import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.travel.TravelModel

interface RestCountriesRepository {

    /** Init App by inserting data into Room Database */

    suspend fun initWorldExplorerDatabase(): Boolean

    /** Country Screen */

    suspend fun getCountryBasic(): List<CountryBasicModel>

    /** Detail Country Screen */

    suspend fun getDetailCountries(cca2: String): CountryDetailModel

    /** Quiz Screen */

    suspend fun getQuizOptionsGlobal(correctCca2List: List<String>) : QuizDetailModel

    suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String): QuizDetailModel

    suspend fun convertToQuizDetailModel(result: List<CountryEntity>): QuizDetailModel

    /** Travek Screen */

    suspend fun getRandomCountryNameCca2(): TravelModel
}