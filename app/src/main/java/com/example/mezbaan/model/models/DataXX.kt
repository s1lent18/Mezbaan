package com.example.mezbaan.model.models

data class DataXX(
    val address: String,
    val averageRating: Double,
    val id: Int,
    val images: List<String>,
    val menuItems: List<MenuItem>,
    val name: String,
    val packages: List<Package>,
    val userId: Int
)