package com.example.worldexplorer.core.bitmapconverter

import android.graphics.Bitmap

/** Creamos una interfaz para respetar el principio de Inversion de Dependencias (Dependency Inversion)
 *  que propone lo siguiente:
 *      - Establece que los módulos de alto nivel no deberían depender de los módulos de nivel
 *        inferior, sino que ambos deberían depender de abstracciones
 *
 *  Esto implica que la implementación concreta de un módulo de nivel inferior no deberia ser
 *  conocida por modulos de nivel superior. En nuestro caso si queremos usar alguna de las
 *  funciones de BitmapConverter en alguna clase, esta no debe saber la implementación de esta
 *  interfaz ya que pueden haber varias versiones, y si dependemos de la implementacion en lugar
 *  de la interfaz, cada vez que cambiemos la implementacion de uno de sus métodos deberemos cambiar
 *  tambien el codigo de la clase de nivel superior.*/
interface BitmapConverter {
    suspend fun getBitmapFromUrl(imageUrl: String): Bitmap
}