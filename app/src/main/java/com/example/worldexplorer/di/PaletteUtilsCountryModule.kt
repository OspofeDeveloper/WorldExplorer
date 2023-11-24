package com.example.worldexplorer.di

import com.example.worldexplorer.core.paletteutils.PaletteUtils
import com.example.worldexplorer.core.paletteutils.PaletteUtilsCountryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

/** Las implementaciones de los dos Palette los tengo separados porque estan en contextos diferentes,
 *  este lo inyectamos en el repository por eso tiene que ser de tipo SingletonComponent, mientras
 *  que el otro lo inyectamos en un viewmodel, por lo que tiene que ser ViewModelComponent*/
@Module
@InstallIn(SingletonComponent::class)
abstract class PaletteUtilsCountryModule {
    @Binds
    @Named("paletteUtilsCountry")
    abstract fun providePaletteUtilsCountry(
        paletteUtilsImpl: PaletteUtilsCountryImpl
    ): PaletteUtils
}