package com.example.worldexplorer.motherobject

import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef
import com.example.worldexplorer.data.database.entities.relations.CountryDetailWithBorder

object WorldExplorerMotherObject {

    val genericCountryEntityList = listOf(
        CountryEntity(
            cca2 = "ES",
            name = "Spain",
            area = 505992.0,
            capital = "Madrid",
            cca3 = "ESP",
            continents = "Europe",
            population = 47351567,
            region = "Europe",
        ),
        CountryEntity(
            cca2 = "PT",
            name = "Portugal",
            area = 508312.0,
            capital = "Lisbon",
            cca3 = "PRT",
            continents = "Europe",
            population = 471567,
            region = "Europe",
        ),
        CountryEntity(
            cca2 = "IT",
            name = "Italy",
            area = 508425.0,
            capital = "Rome",
            cca3 = "ITA",
            continents = "Europe",
            population = 276382,
            region = "Europe",
        ),
        CountryEntity(
            cca2 = "TH",
            name = "Thailand",
            area = 263512.0,
            capital = "Bangkok",
            cca3 = "THA",
            continents = "Asia",
            population = 27363821,
            region = "Asia",
        ),
        CountryEntity(
            cca2 = "RU",
            name = "Russia",
            area = 2635123749.0,
            capital = "Moscow",
            cca3 = "RUS",
            continents = "Europe, Asia",
            population = 393638211,
            region = "Europe",
        ),
        CountryEntity(
            cca2 = "US",
            name = "United States",
            area = 72383270.0,
            capital = "Washington DC",
            cca3 = "USA",
            continents = "North America",
            population = 27123097,
            region = "Americas",
        ),
    )

    val genericCountryBasicModelList = listOf(
        CountryEntity(
            cca2 = "ES",
            name = "Spain",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "PT",
            name = "Portugal",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "IT",
            name = "Italy",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "TH",
            name = "Thailand",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "RU",
            name = "Russia",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "US",
            name = "United States",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
    )

    val genericBorderEntityList = listOf(
        BorderEntity(
            cca3 = "ESP",
            cca2 = "ES",
            name = "Spain"
        ),
        BorderEntity(
            cca3 = "PRT",
            cca2 = "PT",
            name = "Portugal"
        ),
        BorderEntity(
            cca3 = "MAR",
            cca2 = "MA",
            name = "Morocco"
        )
    )

    val genericCountryDetailBorderCrossRef = listOf(
        CountryDetailBorderCrossRef(
            cca2 = "ES",
            cca3 = "PRT"
        ),
        CountryDetailBorderCrossRef(
            cca2 = "ES",
            cca3 = "MAR"
        ),
        CountryDetailBorderCrossRef(
            cca2 = "PT",
            cca3 = "ESP"
        )
    )

    val europeanCountriesBasicModelList = listOf(
        CountryEntity(
            cca2 = "ES",
            name = "Spain",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "PT",
            name = "Portugal",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "IT",
            name = "Italy",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        ),
        CountryEntity(
            cca2 = "RU",
            name = "Russia",
            cca3 = null,
            area = null,
            capital = null,
            population = null,
            continents = null,
            region = null
        )
    )
}