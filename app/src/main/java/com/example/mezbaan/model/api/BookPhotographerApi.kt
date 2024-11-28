package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.requests.BookingReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookPhotographerApi {

    @POST("/bookings/photography")
    suspend fun photographerBooking(
        @Header("Authorization") token: String,
        @Body photographerreq: PhotographerBook
    ) : Response<BookingReq>
}