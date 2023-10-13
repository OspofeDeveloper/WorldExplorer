package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.CountriesEntity

data class CountriesModel (
    var cca2: String,
    var name: String,
) {
    fun toDatabase(): CountriesEntity {
        return CountriesEntity(name = name, cca2 = cca2)
    }
}