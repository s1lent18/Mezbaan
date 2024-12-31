package com.example.mezbaan.model.models

data class DataX(
    val amenities: List<Amenity?>,
    val averageRating: Int,
    val coverImages: List<String>,
    val description: String,
    val id: Int,
    val managerName: String,
    val managerNumber: String,
    val name: String,
    val userId: Int,
    val otherImages: List<String>
)