package com.example.mezbaan.model.models

data class DataXX(
    val address: String,
    val averageRating: Double,
    val coverImages: List<String>,
    val id: Int,
    val menuItems: List<MenuItem>,
    val name: String,
    val packages: List<Package>,
    val userId: Int
)