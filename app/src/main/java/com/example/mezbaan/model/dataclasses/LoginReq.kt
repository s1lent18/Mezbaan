package com.example.mezbaan.model.dataclasses

data class LoginReq(
    val email: String,
    val password: String,
    val roleId: String = "3"
)
