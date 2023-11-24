package com.example.worldexplorer.util

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Error<T>(val error: String) : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
}