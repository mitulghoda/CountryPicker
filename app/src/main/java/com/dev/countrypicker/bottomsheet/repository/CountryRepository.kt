package com.dev.countrypicker.bottomsheet.repository

import com.dev.countrypicker.bottomsheet.network.ApiService
import javax.inject.Inject
class CountryRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCountries() = apiService.getCountries()
}