package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleVenueBookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSingleVenueBookingApi {

    @GET("/bookings/venue/{id}")
    suspend fun getSingleVenueBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<SingleVenueBookingModel>
}