package com.example.worldexplorer.di

import com.example.worldexplorer.core.paletteutils.PaletteUtils
import com.example.worldexplorer.core.paletteutils.PaletteUtilsCountryDetailImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaletteUtilsCountryDetailModule {

    /** Como tenemos 2 implementaciones diferentes pero inyectamos siempre la misma interfaz,
     *  tenemos que etiquetarlas de alguna forma para que Hilt sepa que implementacion es la que
     *  tiene que inyectar. Eso lo hacemos con @Named*/
    @Binds
    @Named("paletteUtilsCountryDetail")
    abstract fun providePaletteUtilsCountryDetail(
        paletteUtilsImpl: PaletteUtilsCountryDetailImpl
    ): PaletteUtils
}