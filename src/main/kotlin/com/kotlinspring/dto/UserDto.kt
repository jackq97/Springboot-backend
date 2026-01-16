package com.kotlinspring.dto

import com.kotlinspring.controller.user.UserRequest
import java.util.UUID

data class UserDto(
    val id: UUID?,
    val email: String,
    val password: String,
    val role: String
) {
    constructor(request: UserRequest) : this(
        null,
        request.email,
        request.password,
        Role.USER.name
    )
}

enum class Role {
    USER, ADMIN
}