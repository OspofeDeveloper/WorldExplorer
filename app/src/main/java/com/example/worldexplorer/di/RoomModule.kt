package com.example.worldexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.worldexplorer.data.database.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val COUNTRY_DATABASE_NAME = "country_database"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CountryDatabase::class.java, COUNTRY_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideCountryDao(db:CountryDatabase) = db.getCountryDao()
}