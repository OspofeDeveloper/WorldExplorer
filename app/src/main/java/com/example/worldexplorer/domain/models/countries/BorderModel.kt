package com.example.worldexplorer.domain.models.countries

import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryBasicEntity

data class BorderModel(
    var cca3: String,
    var cca2: String,
    var name: String
) {
    fun toDatabase(): BorderEntity =
        BorderEntity(
            cca3 = cca3,
            cca2 = cca2,
            name = name,
        )
}
