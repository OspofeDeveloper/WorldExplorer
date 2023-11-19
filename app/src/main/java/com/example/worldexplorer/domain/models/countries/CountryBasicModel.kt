package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.CountryBasicEntity

data class CountryBasicModel(
    var cca2: String,
    var name: String
) {
    fun toDatabase(): CountryBasicEntity =
        CountryBasicEntity(
            cca2 = cca2,
            name = name,
        )
}
