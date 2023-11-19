package com.example.worldexplorer.data

import android.util.Log
import com.example.worldexplorer.data.database.dao.WorldExplorerDao
import com.example.worldexplorer.data.database.entities.CountryBasicEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder
import com.example.worldexplorer.data.network.RestCountriesApiService
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.BorderModel
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
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
                val listCountryBasicEntity = listApiResponse.map { it.toCountryBasicEntity() }
                val listCountryDetailEntity = listApiResponse.map { it.toCountryDetailEntity() }
                val listBorderEntity = listApiResponse.map { it.toBorderEntity() }

                worldExplorerDao.apply {
                    deleteAllCountryBasic()
                    deleteAllCountryDetail()
                    deleteAllBorder()
                    deleteAllDetailBorderCrossRef()

                    insertAllCountryBasic(listCountryBasicEntity)
                    insertAllCountryDetail(listCountryDetailEntity)
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
        return worldExplorerDao.getAllBasicCountries().map { it.toDomain() }
    }

    override suspend fun getAllCountriesBasicOrderAsc(): List<CountryBasicModel> {
        return worldExplorerDao.getAllCountriesBasicOrderAsc().map { it.toDomain() }
    }

    override suspend fun getAllCountriesBasicOrderDesc(): List<CountryBasicModel> {
        return worldExplorerDao.getAllCountriesBasicOrderDesc().map { it.toDomain() }
    }


    /** Detail Country Screen */
    override suspend fun getDetailCountries(cca2: String): List<CountryDetailWithBorder> {
        return worldExplorerDao.getBordersOfCountryDetail(cca2)
    }


    /** Quiz Screen
    override suspend fun getCountriesByRegion(region: String): List<CountryBasicModel> {
    return worldExplorerDao.getCountriesByRegion(region).map { it.toDomain() }
    }

    override suspend fun getRandomCountryNameCca2(): Pair<String, String> {
    val cca2: String = worldExplorerDao.getRandomCca2()
    val name: String = worldExplorerDao.getNameFromCca2(cca2)

    return Pair(name, cca2)
    } */

}