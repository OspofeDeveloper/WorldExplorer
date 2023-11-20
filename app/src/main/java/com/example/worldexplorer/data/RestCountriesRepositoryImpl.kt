package com.example.worldexplorer.data

import android.util.Log
import com.example.worldexplorer.data.database.dao.WorldExplorerDao
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.data.network.RestCountriesApiService
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizOptionModel
import javax.inject.Inject

class RestCountriesRepositoryImpl @Inject constructor(
    private val apiService: RestCountriesApiService,
    private val worldExplorerDao: WorldExplorerDao
) : RestCountriesRepository {

    /** En el caso de hacer peticiones a una API de internet metemos la función en un bloque
    try catch que nos proporciona kotlin para controlar si la respuesta de la API ha ido de
    forma correcta */
    override suspend fun initWorldExplorerDatabase(): Boolean {
        kotlin.runCatching { apiService.getWorldExplorerInfoFromAPI() }
            .onSuccess { listApiResponse ->
                val listCountryDetail = listApiResponse.map { it.toCountryEntity() }
                val listBorderEntity = listApiResponse.map { it.toBorderEntity() }

                worldExplorerDao.apply {
                    deleteAllCountry()
                    deleteAllBorder()
                    deleteAllDetailBorderCrossRef()

                    insertAllCountry(listCountryDetail)
                    insertAllBorders(listBorderEntity)

                    listApiResponse.forEach { apiResponse ->
                        apiResponse.borders?.forEach {
                            val crossRef = CountryDetailBorderCrossRef(apiResponse.cca2, it)
                            insertCountryDetailBorderCrossRef(crossRef)
                        }
                    }
                }

                return true
            }
            .onFailure {
                Log.i("repository", "Error in initWorldExplorerDatabase: ${it.message}")
            }
        return false
    }

    /**
    En el caso de hacer operaciones sobre Room no hace falta meter el codigo dentro de un bloque
    try/catch ya que este no hace operaiones en la red, sino de forma local
     */
    override suspend fun getCountryBasic(): List<CountryBasicModel> {
        return worldExplorerDao.getAllBasicCountries().map { it.toCountryBasicModel() }
    }

    override suspend fun getAllCountriesBasicOrderAsc(): List<CountryBasicModel> {
        return worldExplorerDao.getAllCountriesBasicOrderAsc().map { it.toCountryBasicModel() }
    }

    override suspend fun getAllCountriesBasicOrderDesc(): List<CountryBasicModel> {
        return worldExplorerDao.getAllCountriesBasicOrderDesc().map { it.toCountryBasicModel() }
    }


    /** Detail Country Screen */
    override suspend fun getDetailCountries(cca2: String): CountryDetailModel {
        return worldExplorerDao.getBordersOfCountryDetail(cca2).toDomain()
    }


    /** Quiz Screen */
    override suspend fun getQuizOptionsGlobal(correctCca2List: List<String>): QuizDetailModel {
        val result = worldExplorerDao.getQuizOptionsGlobal(correctCca2List)
        return convertToQuizDetailModel(result)
    }

    override suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String)
            : QuizDetailModel {
        val result = worldExplorerDao.getQuizOptionsByRegion(correctCca2List, region)
        Log.d("Pozo", "result: $result")
        return convertToQuizDetailModel(result)
    }

    override suspend fun convertToQuizDetailModel(result: List<CountryEntity>): QuizDetailModel {
        val listOptions = mutableListOf<QuizOptionModel>()

        result.forEachIndexed { index, option ->
            listOptions.add(QuizOptionModel(option.name, (index == 0)))
        }

        return QuizDetailModel(
            cca2 = result.first().cca2,
            options = listOptions.shuffled()
        )
    }

    /** Travel Screen */
    override suspend fun getRandomCountryNameCca2(): CountryBasicModel {
        return worldExplorerDao.getRandomCca2().toCountryBasicModel()
    }

}