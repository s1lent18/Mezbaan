package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.requests.EditReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditVenueBookingApi {

    @PUT("/bookings/venue/{id}")
    suspend fun editVenueBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body venuereq: VenueBook
    ) : Response<EditReq>
}