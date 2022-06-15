package com.dev.countrypicker.bottomsheet.network

import com.dev.countrypicker.bottomsheet.model.CountryModel
import com.dev.countrypicker.bottomsheet.model.ListBaseModel
import com.dev.countrypicker.bottomsheet.model.ObjectBaseModel
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET(NetworkURL.GET_COUNTRY_LIST)
    suspend fun getCountries(): Response<ArrayList<CountryModel>>

}