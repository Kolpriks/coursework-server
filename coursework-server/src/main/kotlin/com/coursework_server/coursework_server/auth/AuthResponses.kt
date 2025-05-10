package com.coursework_server.coursework_server.auth

data class UserResponse(
    val id: Long,
    val email: String,
    val role: String
)
