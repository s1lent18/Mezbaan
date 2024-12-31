package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleDecoratorBookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSingleDecoratorBookingApi {

    @GET("/bookings/decorationService/{id}")
    suspend fun getSingleDecoratorBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<SingleDecoratorBookingModel>
}