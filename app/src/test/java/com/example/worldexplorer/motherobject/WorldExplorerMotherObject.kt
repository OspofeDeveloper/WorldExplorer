package com.example.worldexplorer.motherobject

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.example.worldexplorer.data.network.response.Name
import com.example.worldexplorer.data.network.response.WorldExplorerResponse
import com.example.worldexplorer.domain.models.countries.CountryBasicModel
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizDetailModel
import com.example.worldexplorer.domain.models.detailquiz.QuizOptionModel
import com.example.worldexplorer.domain.models.travel.TravelModel

object WorldExplorerMotherObject {

    /** Response de API generico, sin nada especial*/
    val genericResponse = WorldExplorerResponse(
        cca2 = "ES",
        name = Name(common = "Spain"),
        area = 505992.0,
        capital = listOf("Madrid"),
        cca3 = "ESP",
        continents = listOf("Europe"),
        population = 47351567,
        region = "Europe",
        borders = listOf("AND", "FRA", "GIB", "PRT", "MAR")
    )

    val nullResponse = WorldExplorerResponse(
        cca2 = "ES",
        name = Name(common = "Spain"),
        area = null,
        capital = null,
        cca3 = "ESP",
        continents = null,
        population = null,
        region = null,
        borders = null
    )

    val bigListsResponse = WorldExplorerResponse(
        cca2 = "ES",
        name = Name(common = "Spain"),
        area = 505992.0,
        capital = listOf("Madrid", "Barcelona", "Seville"),
        cca3 = "ESP",
        continents = listOf("Europe", "Asia", "Oceania"),
        population = 47351567,
        region = "Europe",
        borders = listOf("AND", "FRA", "GIB", "PRT", "MAR")
    )

    val strangeSymbolName = WorldExplorerResponse(
        cca2 = "ES",
        name = Name(common = "Spain"),
        area = 505992.0,
        capital = listOf("Madrid"),
        cca3 = "ESP",
        continents = listOf("Europe"),
        population = 47351567,
        region = "Europe",
        borders = listOf("AND", "FRA", "GIB", "PRT", "MAR")
    )

    val genericCountryBasicModelList = listOf(
        CountryBasicModel(
            cca2 = "TH",
            name = "Thailand",
            imageUrl = "https://flagcdn.com/w320/ru.png",
            backgroundDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.WHITE, Color.BLACK)
            )
        ),
        CountryBasicModel(
            cca2 = "RU",
            name = "Russia",
            imageUrl = "https://flagcdn.com/w320/ru.png",
            backgroundDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.WHITE, Color.BLACK)
            )
        ),
        CountryBasicModel(
            cca2 = "US",
            name = "United States",
            imageUrl = "https://flagcdn.com/w320/us.png",
            backgroundDrawable = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.WHITE, Color.BLACK)
            )
        ),
    )

    /** Detail Country Screen functions
    suspend fun getDetailCountries(cca2: String): CountryDetailModel */
    val countryDetailModel = CountryDetailModel(
        area = 505992.0,
        capital = "Madrid",
        cca3 = "ESP",
        continents = "Europe",
        population = 47351567,
        region = "Europe",
        imageUrl = "https://flagcdn.com/w320/es.png",
        cca2Borders = listOf("AN", "FR", "MA", "PT"),
        nameBorders = listOf("Andorra", "France", "Morocco", "Portugal"),
    )

    /** Quiz Screen operations
    suspend fun getQuizOptionsGlobal(correctCca2List: List<String>) : QuizDetailModel
    suspend fun getQuizOptionsByRegion(correctCca2List: List<String>, region: String): QuizDetailModel
    suspend fun convertToQuizDetailModel(result: List<CountryEntity>): QuizDetailModel */
    val quizDetailModel = QuizDetailModel(
        cca2Correct = "ES",
        imageUrl = "https://flagcdn.com/w320/es.png",
        options = listOf(
            QuizOptionModel(
                "Sweden",
                false
            ),
            QuizOptionModel(
                "Spain",
                true
            ),
            QuizOptionModel(
                "Croatia",
                false
            ),
            QuizOptionModel(
                "Norway",
                false
            ),
        )
    )


    /** Travek Screen operations
    suspend fun getRandomCountryNameCca2(): TravelModel */
    val travelModel = TravelModel(
        cca2 = "BJ",
        imageUrl = "https://flagcdn.com/w320/bj.png",
        name = "Benin"
    )
}