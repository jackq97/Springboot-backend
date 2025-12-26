package com.kotlinspring.repository

import com.kotlinspring.dto.Role
import com.kotlinspring.dto.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(
    private val encoder: BCryptPasswordEncoder
) {
private val users = mutableListOf(
    User(
        id = null,
        email = "test@gmail1.com",
        password = encoder.encode("test1"),
        role = Role.USER
    ),
    User(
        id = null,
        email = "test@gmail2.com",
        password = encoder.encode("test2"),
        role = Role.ADMIN
    ),
    User(
        id = null,
        email = "test@gmail3.com",
        password = encoder.encode("test3"),
        role = Role.USER
    )
)

    fun save(user: User): Boolean {
        val updated = user.copy(
            password = encoder.encode(user.password),
        )
        return users.add(updated)
    }
    fun findByEmail(email: String): User? =
        users.firstOrNull { it.email == email }

    fun findAll(): List<User> =
        users

    fun findByUUID(uuid: UUID): User? =
        users
            .firstOrNull { it.id == uuid }

    fun deletedByUUID(uuid: UUID): Boolean {
        val foundUser = findByUUID(uuid)

        return foundUser.let {
            users.remove(it)
        }
    }
}