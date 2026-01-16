package com.kotlinspring.dto

import java.util.UUID

data class BookDto(
    val id: UUID?,
    val name: String,
    val category: String,
)
