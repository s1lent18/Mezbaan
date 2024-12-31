package com.example.mezbaan.model.models

data class BookedAmenity(
    val amenity: String,
    val cost: String,
    val count: Int,
    val decorationAmenityId: Int,
    val id: Int
)