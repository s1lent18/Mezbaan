package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.VenueBook
import com.example.mezbaan.model.models.VenueReqHandle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VenueApi {

    @POST("/VenueBooking")
    suspend fun venueBooking(
        @Body venuebooking: VenueBook
    ) : Response<VenueReqHandle>
}