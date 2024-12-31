package com.example.mezbaan.model.api

import com.example.mezbaan.model.models.BookingModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GetBookingsApi {

    @GET("/appUser/bookings")
    suspend fun getBookings(
        @Header("Authorization") token: String
    ) : Response<BookingModel>
}