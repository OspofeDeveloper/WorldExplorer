package com.example.worldexplorer.di

import com.example.worldexplorer.data.RestCountriesRepositoryImpl
import com.example.worldexplorer.domain.RestCountriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Con InstallIn le indicamos a que nivel de la aplicación se pueden usar las dependencias. En este
 *  caso indicamos que es SingletonComponent, de tal forma que podemos inyectar las dependencias de
 *  este modulo durante el ciclo de vida de nuestra aplicación. En el caso de usar otra como
 *  ActivityComponent indicamos que podemos usar las dependencias del modulo en cualquier Activity
 *  de nuestra aplicación. */
@Module
@InstallIn(SingletonComponent::class)
abstract class InterfaceModule {

    /** En el caso de inyectar interfaces en lugar de crear un @Provider creamos un @Bind. En este
     *  caso le proporcionamos a Hilt la siguiente información:
     *      1. El tipo de datos que devuelve la función le indica a Hilt las instancias de qué
     *         interfaz proporciona la función. En este caso RestCountriesRepository.
     *      2. El parametro de la funcion indica a Hilt que implementacion debe proporcionar
     *
     * Esto lo hacemos para reducir la cantidad de codigo generado, mejorando el rendimiento de la
     * aplicación cuando esta vaya creciendo.*/
    @Binds
    @Singleton
    abstract fun provideRestCountriesRepository(
        restCountriesRepositoryImpl: RestCountriesRepositoryImpl
    ): RestCountriesRepository
}