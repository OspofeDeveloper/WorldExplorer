package com.example.worldexplorer.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "borders_table")
data class BorderEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cca3") val cca3: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cca2") val cca2: String
)