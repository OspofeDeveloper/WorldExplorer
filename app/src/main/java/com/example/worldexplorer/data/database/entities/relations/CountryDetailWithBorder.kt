package com.example.worldexplorer.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryDetailEntity
import com.example.worldexplorer.domain.models.detailcountries.CountryDetailModel

/** En las relaciones many-to-many a parte de tener la clase auxiliar CountryDetailBorderCrossRef
 * para encapsular las primaryKeys de ambas tablas necesitamos crear las relaciones al igual que
 * hicimos con CountryBasicWithCountryDetail, pero en este caso tendremos que hacer dos clases, en
 * cada una de ellas tendremos una tabla que será la ebedded y la otra será la relacionada. Osea
 * que realmente lo que hacemos es definir 2 clases diferentes con relaciones one-to-many, por eso
 * en este caso border es una lista de Borders */
data class CountryDetailWithBorder(
    @Embedded val countryDetail: CountryDetailEntity,

    /** En este caso como tenemos una relacion many-to-many tenemos que especificar la clase que
     *  contiene realmente la relación entre ambas clases, que en este caso es la clase auxiliar
     *  CountryDetailBorderCrossRef */
    @Relation(
        parentColumn = "cca2",
        entityColumn = "cca3",
        associateBy = Junction(CountryDetailBorderCrossRef::class)
    )

    val border: List<BorderEntity>
) {
    fun toDomain() : CountryDetailModel =
        CountryDetailModel(
            cca2 = countryDetail.cca2,
            cca3 = countryDetail.cca3,
            area = countryDetail.area,
            capital = countryDetail.capital,
            continents = countryDetail.continents,
            population = countryDetail.population,
            region = countryDetail.region,
            cca2Borders = border.map { it.cca2 },
            nameBorders = border.map { it.name }
        )
}
