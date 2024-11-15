package com.example.mezbaan.model.dataclasses

data class LoginReq(
    val username: String,
    val password: String,
    val roleId: Int = 3
)
