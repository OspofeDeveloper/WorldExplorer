package com.example.worldexplorer.di

import com.example.worldexplorer.core.bitmapconverter.BitmapConverter
import com.example.worldexplorer.core.bitmapconverter.BitmapConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BitmapConverterModule {
    @Binds
    abstract fun provideBitmapConverter(
        bitmapConverterImpl: BitmapConverterImpl
    ): BitmapConverter
}