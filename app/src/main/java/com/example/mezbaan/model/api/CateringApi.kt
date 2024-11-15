package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.CateringBook
import com.example.mezbaan.model.models.CateringReqHandle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CateringApi {

    @POST("/CateringBooking")
    suspend fun cateringBooking(
        @Body cateringbooking: CateringBook
    ) : Response<CateringReqHandle>
}