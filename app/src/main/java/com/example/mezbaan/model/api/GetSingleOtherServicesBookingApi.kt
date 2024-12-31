package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleOtherServicesBookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSingleOtherServicesBookingApi {

    @GET("/bookings/otherService/{id}")
    suspend fun getSingleOtherServicesBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<SingleOtherServicesBookingModel>
}