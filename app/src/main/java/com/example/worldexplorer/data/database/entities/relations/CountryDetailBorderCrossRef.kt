package com.example.worldexplorer.data.database.entities.relations

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.worldexplorer.domain.models.countries.BorderModel

/** Creamos esta clase como una clase de soporte entre CountryDetail y Borders, por lo que cada
 *  vez que insertamos un nuevo país o un , ya que entre
 *  ellas tienen una relacion de many-to-many y por lo tanto en vez de tener una priaryKey en
 *  común como habiamos visto hasta ahora, ahora cogemos las primaryKey de ambas
 *  (cca2 -> CountryDetail; cca3 -> Border)*/
@Entity(tableName = "detail_borders_cross_ref_table", primaryKeys = ["cca2", "cca3"])
data class CountryDetailBorderCrossRef(
    val cca2: String,
    val cca3: String
)