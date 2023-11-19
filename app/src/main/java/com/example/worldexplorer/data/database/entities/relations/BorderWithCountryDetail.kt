package com.example.worldexplorer.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryDetailEntity

data class BorderWithCountryDetail(
    @Embedded val border: BorderEntity,

    @Relation(
        parentColumn = "cca3",
        entityColumn = "cca2",
        associateBy = Junction(CountryDetailBorderCrossRef::class)
    )

    val countryDetail: List<CountryDetailEntity>
)
