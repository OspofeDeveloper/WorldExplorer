package com.example.worldexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.worldexplorer.data.database.WorldExplorerDatabase
import com.example.worldexplorer.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WorldExplorerDatabase::class.java, Constants.WORLD_EXPLORER_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideWorldExplorerDao(db: WorldExplorerDatabase) = db.getWorldExplorerDao()
}