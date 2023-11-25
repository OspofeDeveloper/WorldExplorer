package com.example.worldexplorer.di

import com.example.worldexplorer.util.paletteutils.PaletteUtils
import com.example.worldexplorer.util.paletteutils.PaletteUtilsCountryDetailImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelInterfacesModule {

    @Binds
    @Named("paletteUtilsCountryDetail")
    abstract fun providePaletteUtilsCountryDetail(
        paletteUtilsImpl: PaletteUtilsCountryDetailImpl
    ): PaletteUtils

}