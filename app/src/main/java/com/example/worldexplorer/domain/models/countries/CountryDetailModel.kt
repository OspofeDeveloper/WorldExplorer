package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.CountryDetailEntity

data class CountryDetailModel(
    var cca2: String,
    var area: Double?,
    var capital: String?,
    var cca3: String?,
    var continents: String?,
    var population: Int?,
    var region: String?
) {
    fun toDatabase(): CountryDetailEntity =
        CountryDetailEntity(
            cca2 = cca2,
            area = area,
            capital = capital,
            cca3 = cca3,
            continents = continents,
            population = population,
            region = region
        )
}
