package com.example.mezbaan.model.requests

data class SignUpReq(
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val roleId: String = "3"
)
