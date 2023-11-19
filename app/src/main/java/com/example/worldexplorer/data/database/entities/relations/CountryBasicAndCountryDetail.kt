package com.example.worldexplorer.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.worldexplorer.data.database.entities.CountryBasicEntity
import com.example.worldexplorer.data.database.entities.CountryDetailEntity

data class CountryBasicAndCountryDetail (
    /** @Embedded se utiliza para indicar que la propiedad countryBasic debe ser tratada
     *  como una parte incrustada de la entidad principal, es decir, que las columnas de
     *  CountriyBasicEntity estan y se pueden modificar dentro de CountryBasicWithCountryDetail*/
    @Embedded val countryBasic: CountryBasicEntity,

    /**@Relation se utiliza para indicar que la propiedad countryDetail está relacionada con
     * CountryBasicEntity, es decir, que ambas tablas tienen relacion entre ellas.
     * Se especifican las columnas que deben coincidir (parentColumn y entityColumn), donde
     * parentColumn es el campo de countryBasic (@Embedded) y entityColumn el campo de
     * countryDetail*/
    @Relation(
        parentColumn = "cca2",
        entityColumn = "cca2"
    )
    val countryDetail: CountryDetailEntity
)