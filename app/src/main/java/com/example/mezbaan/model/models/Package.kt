package com.example.mezbaan.model.models

data class Package(
    val id: Int,
    val menuItems: List<MenuItem?>?,
    val name: String,
    val price: Int
)