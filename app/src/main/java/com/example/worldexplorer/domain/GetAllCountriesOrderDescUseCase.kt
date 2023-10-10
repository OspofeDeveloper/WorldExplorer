package com.example.worldexplorer.domain

import com.example.worldexplorer.data.RestCountriesRepository
import com.example.worldexplorer.domain.model.CountryItem
import javax.inject.Inject

class GetAllCountriesOrderDescUseCase @Inject constructor(
    private val repository: RestCountriesRepository
) {

    suspend operator fun invoke(): List<CountryItem> = repository.getAllCountriesOrderDesc()
}