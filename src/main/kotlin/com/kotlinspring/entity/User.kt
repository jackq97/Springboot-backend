package com.kotlinspring.entity

import com.kotlinspring.controller.user.UserRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length=16)
    val id: UUID?,
    val email: String,
    val password: String,
    val role: String
) {
    constructor(request: UserRequest) : this(
        null,
        request.email,
        request.password,
        role = Role.USER.name
    )
}

enum class Role {
    USER, ADMIN
}