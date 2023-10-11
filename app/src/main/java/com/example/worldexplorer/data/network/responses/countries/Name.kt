package com.example.worldexplorer.data.network.responses.countries

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("common") var common: String
)