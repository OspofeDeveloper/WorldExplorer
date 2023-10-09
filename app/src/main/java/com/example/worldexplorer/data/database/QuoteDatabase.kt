package com.example.worldexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worldexplorer.data.database.dao.CountryDao
import com.example.worldexplorer.data.database.entities.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun getCountryDao():CountryDao
}