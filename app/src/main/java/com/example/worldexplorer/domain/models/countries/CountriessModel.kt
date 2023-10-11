package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.CountriesEntity

data class CountriesModel (
    var flagImageUrl: String,
    var name: String,
) {
    fun toDatabase(): CountriesEntity {
        val cca2 = flagImageUrl.dropLast(4).takeLastWhile { it.isLetter() }
        return CountriesEntity(name = name, cca2 = cca2)
    }
}