package com.example.mezbaan.model.dataclasses

data class SignUpReq(
    val username: String,
    val phone: String,
    val email: String,
    val password: String,
    val roleId: Int = 3
)
