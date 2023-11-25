package com.example.worldexplorer.data

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.util.Log
import com.example.worldexplorer.util.bitmapconverter.BitmapConverter
import com.example.worldexplorer.util.paletteutils.PaletteUtils
import com.example.worldexplorer.data.database.dao.WorldExplorerDao
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.data.network.RestCountriesApiService
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizOptionModel
import com.example.worldexplorer.domain.models.travel.TravelModel
import javax.inject.Inject
import javax.inject.Named

class RestCountriesRepositoryImpl @Inject constructor(
    private val apiService: RestCountriesApiService,
    private val worldExplorerDao: WorldExplorerDao,
    private val bitmapConverter: BitmapConverter,
    @Named("paletteUtilsCountry") private val paletteUtilsCountry: PaletteUtils
) : RestCountriesRepository {

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

    /** List Of Countries Screen */

    override suspend fun getCountryBasic(): List<CountryBasicModel> {
        var bitmap: Bitmap
        var backgroundDrawable: GradientDrawable
        worldExplorerDao.getAllBasicCountries()

        return worldExplorerDao.getAllBasicCountries().map {
            bitmap = bitmapConverter.getBitmapFromUrl("https://flagcdn.com/w320/${it.cca2.lowercase()}.png")
            backgroundDrawable = paletteUtilsCountry.getBackgroundGradient(bitmap)
            it.toCountryBasicModel(backgroundDrawable)
        }
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
            cca2Correct = result.first().cca2,
            imageUrl = "https://flagcdn.com/w320/${result.first().cca2.lowercase()}.png",
            options = listOptions.shuffled()
        )
    }

    /** Travel Screen */

    override suspend fun getRandomCountryNameCca2(): TravelModel {
        return worldExplorerDao.getRandomCca2().toTravelModel()
    }

}