package com.kotlinspring.controller.auth

data class AuthenticationRequest(
    val email: String,
    val password: String
)