package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.OtherServicesBook
import com.example.mezbaan.model.requests.BookingReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookOtherServicesApi {

    @POST("/bookings/otherService")
    suspend fun otherservicesBooking(
        @Header("Authorization") token: String,
        @Body otherservicesreq: OtherServicesBook
    ) : Response<BookingReq>
}