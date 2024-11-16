package com.example.mezbaan.model.models

data class User(
    val createdAt: String,
    val email: String,
    val id: Int,
    val image: Any,
    val name: String,
    val phone: String,
    val role: String,
    val roleId: Int
)