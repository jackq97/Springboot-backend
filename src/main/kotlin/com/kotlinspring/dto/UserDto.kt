package com.kotlinspring.dto

import jakarta.persistence.OneToMany
import java.util.UUID

data class User(
    val id: UUID?,
    val email: String,
    val password: String,
    @OneToMany(mappedBy = "user")
    private List<UserFavoritesBook> favoriteBooks;
    val role: Role
)

enum class Role {
    USER, ADMIN
}