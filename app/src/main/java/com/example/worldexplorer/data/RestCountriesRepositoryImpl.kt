package com.example.worldexplorer.data

import android.util.Log
import com.example.worldexplorer.data.database.dao.CountriesDao
import com.example.worldexplorer.data.database.entities.CountriesEntity
import com.example.worldexplorer.data.network.RestCountriesApiService
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountriesModel
import javax.inject.Inject

class RestCountriesRepositoryImpl @Inject constructor(
    private val apiService: RestCountriesApiService,
    private val countryItemDao: CountriesDao
) : RestCountriesRepository {

    /**
        En el caso de hacer peticiones a una API de internet metemos la función en un bloque
        try catch que nos proporciona kotlin para controlar si la respuesta de la API ha ido de
        forma correcta
     */
    override suspend fun getAllCountries(): List<CountriesModel> {
        kotlin.runCatching { apiService.getAllCountries() }
            .onSuccess { listCountriesInfo ->
                val response = listCountriesInfo.map { it.toDomain() }
                clearCountries()
                insertCountries(response.map { it.toDatabase() })
                return response
            }
            .onFailure {
                Log.i("repository", "Ha ocurrido un error: ${it.message}")
            }
        return countryItemDao.getAllCountries().map { it.toDomain() }
    }

    /**
        En el caso de hacer operaciones sobre Room no hace falta meter el codigo dentro de un bloque
        try/catch ya que este no hace operaiones en la red, sino de forma local
     */
    override suspend fun insertCountries(countries:List<CountriesEntity>) {
        countryItemDao.insertAll(countries)
    }

    override suspend fun clearCountries() {
        countryItemDao.deleteAllCountries()
    }

    override suspend fun getAllCountriesOrderAsc(): List<CountriesModel> {
        return countryItemDao.getAllCountriesOrderAsc().map { it.toDomain() }
    }

    override suspend fun getAllCountriesOrderDesc(): List<CountriesModel> {
        return countryItemDao.getAllCountriesOrderDesc().map { it.toDomain() }
    }

    override suspend fun getDetailCountries(cca2: String): CountriesModel? {
        return countryItemDao.getCountriesInfo(cca2)?.toDomain()
    }

    override suspend fun getCountriesByRegion(region: String): List<CountriesModel> {
        return countryItemDao.getCountriesByRegion(region).map { it.toDomain() }
    }
}