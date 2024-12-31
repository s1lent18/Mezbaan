package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleVenueModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetSingleVenueApi {

    @GET("venues/{id}")
    suspend fun getSingleVenue(
        @Path("id") id: Int,
    ): Response<SingleVenueModel>
}