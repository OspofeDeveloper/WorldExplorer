package com.example.worldexplorer.data.database.entities.relations

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "detail_borders_cross_ref_table", primaryKeys = ["cca2", "cca3"])
data class CountryDetailBorderCrossRef(
    val cca2: String,

    @ColumnInfo(index = true)
    val cca3: String
)