package com.example.worldexplorer.data.countries.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worldexplorer.data.countries.database.dao.CountryDao
import com.example.worldexplorer.data.countries.database.entities.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun getCountryDao(): CountryDao
}