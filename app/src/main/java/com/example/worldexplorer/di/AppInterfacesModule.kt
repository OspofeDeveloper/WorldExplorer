package com.example.worldexplorer.di

import com.example.worldexplorer.data.RestCountriesRepositoryImpl
import com.example.worldexplorer.domain.RestCountriesRepository
import com.example.worldexplorer.util.bitmapconverter.BitmapConverter
import com.example.worldexplorer.util.bitmapconverter.BitmapConverterImpl
import com.example.worldexplorer.util.paletteutils.PaletteUtils
import com.example.worldexplorer.util.paletteutils.PaletteUtilsCountryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppInterfacesModule {

    @Binds
    @Singleton
    abstract fun provideRestCountriesRepository(
        restCountriesRepositoryImpl: RestCountriesRepositoryImpl
    ): RestCountriesRepository


    @Binds
    @Singleton
    abstract fun provideBitmapConverter(
        bitmapConverterImpl: BitmapConverterImpl
    ): BitmapConverter

    @Binds
    @Singleton
    @Named("paletteUtilsCountry")
    abstract fun providePaletteUtilsCountry(
        paletteUtilsImpl: PaletteUtilsCountryImpl
    ): PaletteUtils

}