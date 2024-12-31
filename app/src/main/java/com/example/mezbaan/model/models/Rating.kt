package com.example.mezbaan.model.models

data class Rating(
    val bookingId: Int,
    val comments: String,
    val id: Int,
    val rating: Int,
    val serviceId: Int,
    val serviceType: String,
    val userId: Int
)