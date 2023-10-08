package com.example.worldexplorer.ui.listCountries

class ListCountriesProvider {
    companion object {
        private const val BASE_URL = "https://flagcdn.com/w320/"

        val countryList: List<CountriesModel> = listOf(
            CountriesModel("$BASE_URL${"us"}.png", "us"),
            CountriesModel("$BASE_URL${"ca"}.png", "ca"),
            CountriesModel("$BASE_URL${"mx"}.png", "mx"),
            CountriesModel("$BASE_URL${"gb"}.png", "gb"),
            CountriesModel("$BASE_URL${"de"}.png", "de"),
            CountriesModel("$BASE_URL${"fr"}.png", "fr"),
            CountriesModel("$BASE_URL${"jp"}.png", "jp"),
            CountriesModel("$BASE_URL${"cn"}.png", "cn"),
            CountriesModel("$BASE_URL${"br"}.png", "br"),
            CountriesModel("$BASE_URL${"ar"}.png", "ar")
        )
    }
}