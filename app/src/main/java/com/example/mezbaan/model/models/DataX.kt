package com.example.mezbaan.model.models

data class DataX(
    val amenities: List<Amenity?>,
    val description: String,
    val id: Int,
    val images: List<String>,
    val managerName: String,
    val managerNumber: String,
    val name: String,
    val userId: Int
)