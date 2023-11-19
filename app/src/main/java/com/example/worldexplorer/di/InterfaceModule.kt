package com.example.worldexplorer.di

import com.example.worldexplorer.data.RestCountriesRepositoryImpl
import com.example.worldexplorer.data.database.dao.WorldExplorerDao
import com.example.worldexplorer.data.network.RestCountriesApiService
import com.example.worldexplorer.domain.RestCountriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InterfaceModule {
    @Provides
    fun provideRestCountriesRepository(
        apiService: RestCountriesApiService,
        countriesDao: WorldExplorerDao
    ): RestCountriesRepository {
        return RestCountriesRepositoryImpl(apiService, countriesDao)
    }
}