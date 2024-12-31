package com.example.mezbaan.model.api

import com.example.mezbaan.model.requests.DeleteReq
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteDecoratorBookingApi {

    @DELETE("/bookings/decorationService/{id}")
    suspend fun deleteSingleDecorator(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<DeleteReq>
}