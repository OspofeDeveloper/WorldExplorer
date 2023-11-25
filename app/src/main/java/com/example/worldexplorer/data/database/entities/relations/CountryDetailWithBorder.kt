package com.example.worldexplorer.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel

data class CountryDetailWithBorder(
    @Embedded val countryDetail: CountryEntity,

    @Relation(
        parentColumn = "cca2",
        entityColumn = "cca3",
        associateBy = Junction(CountryDetailBorderCrossRef::class)
    )

    val border: List<BorderEntity>
) {

    fun toDomain() : CountryDetailModel =
        CountryDetailModel(
            cca3 = countryDetail.cca3,
            area = countryDetail.area,
            capital = countryDetail.capital,
            continents = countryDetail.continents,
            population = countryDetail.population,
            region = countryDetail.region,
            imageUrl = "https://flagcdn.com/w320/${countryDetail.cca2.lowercase()}.png",
            cca2Borders = border.map { it.cca2 },
            nameBorders = border.map { it.name }
        )
}
