package com.example.mezbaan.model.requests

data class LoginReq(
    val email: String,
    val password: String,
    val roleId: String = "3"
)
