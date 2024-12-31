package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.requests.BookingReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface BookVenueApi {

    @POST("/bookings/venue")
    suspend fun venueBooking(
        @Header("Authorization") token: String,
        @Body venuereq: VenueBook
    ) : Response<BookingReq>
}