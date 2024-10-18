package com.example.mezbaan.model.dataclasses

data class LoginReq(
    val username: String,
    val password: String
)

data class LoginHandle(
    val result: Boolean
)
