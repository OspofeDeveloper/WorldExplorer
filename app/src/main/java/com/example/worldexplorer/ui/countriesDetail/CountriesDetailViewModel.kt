package com.example.worldexplorer.ui.countriesDetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CountriesDetailViewModel @Inject constructor() : ViewModel() {

    private var _state = MutableStateFlow<CountriesDetailState>(CountriesDetailState.Loading)
    val state:StateFlow<CountriesDetailState> = _state
}