package com.example.worldexplorer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.worldexplorer.data.database.dao.CountriesDao
import com.example.worldexplorer.data.database.entities.CountriesEntity

@Database(entities = [CountriesEntity::class], version = 1)
abstract class CountryDatabase: RoomDatabase() {

    abstract fun getCountryDao(): CountriesDao
}