package com.kotlinspring.repository

import com.kotlinspring.dto.UserDto
import com.kotlinspring.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: CrudRepository<User, Int> {
    fun findUserDtosByEmail(email: String): UserDto?
    fun findUserDtosById(id: UUID): UserDto?
}