package com.example.worldexplorer.data.network.response

import com.google.gson.annotations.SerializedName

data class Name(
    @SerializedName("common") var common: String
)