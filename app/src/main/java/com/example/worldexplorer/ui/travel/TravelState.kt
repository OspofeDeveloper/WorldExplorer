package com.example.worldexplorer.ui.travel

sealed class TravelState {
    data object Loading : TravelState()
    data class Error(val error: String) : TravelState()
    data class Success(val travelInfo: Pair<String, String>) : TravelState()
}