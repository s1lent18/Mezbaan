package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.SingleCateringBookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetSingleCateringBookingApi {

    @GET("/bookings/cateringService/{id}")
    suspend fun getSingleCateringBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<SingleCateringBookingModel>
}