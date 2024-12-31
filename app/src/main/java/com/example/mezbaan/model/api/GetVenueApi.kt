package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.VenueModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVenueApi {

    @GET("/venues")
    suspend fun getVenues(): Response<VenueModel>
}