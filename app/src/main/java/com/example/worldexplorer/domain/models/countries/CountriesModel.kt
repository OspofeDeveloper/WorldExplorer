package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.CountriesEntity

data class CountriesModel(
    var cca2: String,
    var name: String,
    var area: Double?,
    var borders: String?,
    var capital: String?,
    var cca3: String?,
    var continents: String?,
    var population: Int?
) {
    fun toDatabase(): CountriesEntity {
        return CountriesEntity(
            cca2 = cca2,
            name = name,
            area = area,
            borders = borders,
            capital = capital,
            cca3 = cca3,
            continents = continents,
            population = population
        )
    }
}