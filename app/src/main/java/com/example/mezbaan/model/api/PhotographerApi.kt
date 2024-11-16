package com.example.mezbaan.model.api

import com.example.mezbaan.model.dataclasses.PhotographerBook
import com.example.mezbaan.model.models.PhotographerReqHandle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PhotographerApi {

    @POST("/PhotographerBooking")
    suspend fun photographerBooking(
        @Body photographerbooking: PhotographerBook
    ) : Response<PhotographerReqHandle>

}