package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SinglePhotographerBookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSinglePhotographerBookingApi {

    @GET("/bookings/photography/{id}")
    suspend fun getSinglePhotographerBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<SinglePhotographerBookingModel>
}