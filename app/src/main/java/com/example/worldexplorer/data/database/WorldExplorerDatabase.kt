package com.example.worldexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worldexplorer.data.database.dao.WorldExplorerDao
import com.example.worldexplorer.data.database.entities.BorderEntity
import com.example.worldexplorer.data.database.entities.CountryEntity
import com.example.worldexplorer.data.database.entities.relations.CountryDetailBorderCrossRef

@Database(
    entities = [
        CountryEntity::class,
        BorderEntity::class,
        CountryDetailBorderCrossRef::class
    ],
    version = 1
)
abstract class WorldExplorerDatabase : RoomDatabase() {
    abstract fun getWorldExplorerDao(): WorldExplorerDao
}