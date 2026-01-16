package com.kotlinspring.service

import com.kotlinspring.controller.user.UserRequest
import com.kotlinspring.dto.UserDto
import com.kotlinspring.entity.User
import com.kotlinspring.repository.UserRepository
import mu.KLogging
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bcryptEncoder: BCryptPasswordEncoder
) {

    companion object : KLogging()

    fun createUser(userDto: UserDto): UserDto? {
        val userEntity = userDto.let {
            User(UserRequest(
                email = it.email,
                password = bcryptEncoder.encode(it.password),
            ))
        }
        val foundUser = userRepository.findUserDtosByEmail(userEntity.email)

        if (foundUser == null) {
            userRepository.save(userEntity)
            logger.info { "Added new course: $userEntity" }
            return userDto
        } else {
            throw RuntimeException("User already exist.")
        }
    }


    fun findByUUID(uuid: UUID): UserDto {
        userRepository.findUserDtosById(uuid)?.let {
            return it
        }
        throw RuntimeException(
            "User with UUID $uuid not found."
        )
    }

    fun findByAll(): List<String> {
        val email = userRepository.findAll().map { it.email }
        return email
    }

}