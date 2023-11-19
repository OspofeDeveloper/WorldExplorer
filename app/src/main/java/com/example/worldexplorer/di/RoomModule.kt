package com.example.worldexplorer.di

import android.content.Context
import androidx.room.Room
import com.example.worldexplorer.data.database.WorldExplorerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val WORLD_EXPLORER_DATABASE_NAME = "world_explorer_database"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WorldExplorerDatabase::class.java, WORLD_EXPLORER_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideWorldExplorerDao(db: WorldExplorerDatabase) = db.getWorldExplorerDao()
}