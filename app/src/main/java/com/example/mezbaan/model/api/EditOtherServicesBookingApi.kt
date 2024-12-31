package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.OtherServicesBook
import com.example.mezbaan.model.requests.EditReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface EditOtherServicesBookingApi {

    @PUT("/bookings/otherService/{id}")
    suspend fun editOtherServicesBooking(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body otherservicesreq: OtherServicesBook
    ) : Response<EditReq>
}