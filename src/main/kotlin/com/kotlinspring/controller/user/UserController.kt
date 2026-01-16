package com.kotlinspring.controller.user

import com.kotlinspring.dto.Role
import com.kotlinspring.dto.UserDto
import com.kotlinspring.service.UserService
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@CrossOrigin
@RestController
@RequestMapping("v1/user")
class UserController(private val userService: UserService) {

    companion object : KLogging()

    @PostMapping
    fun createUser(@RequestBody userRequest: UserRequest): UserResponse =
        userService.createUser(
            userDto = UserDto(userRequest)
        )?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Cannon create a user.")

    @GetMapping
    fun listAll(): List<String> =
        userService.findByAll()
            .map { it }

    @GetMapping("/{uuid}")
    fun findByUUID(@PathVariable uuid: UUID): UserResponse =
        userService.findByUUID(uuid)
            ?.toResponse()
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cannon find a user.")

    /*@DeleteMapping("/{uuid}")
    fun deleteByUUID(@PathVariable uuid: UUID) : ResponseEntity<Boolean> {
        val success = userService.deleteById(uuid)
        return if (success) {
            ResponseEntity.noContent()
                .build()
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cannon find a user.")
        }
    }*/

   /* private fun UserRequest.toModel(): UserDto =
        UserDto(
            id = null,
            email = this.email,
            password = this.password,
            role = Role.USER,
        )
*/
    private fun UserDto.toResponse(): UserResponse =
        UserResponse(
            email = this.email
        )
}