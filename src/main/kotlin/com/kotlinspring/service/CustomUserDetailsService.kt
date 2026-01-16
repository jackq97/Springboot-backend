package com.kotlinspring.service

import com.kotlinspring.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.kotlinspring.dto.UserDto

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findUserDtosByEmail(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not Found!")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        return User.builder()
            .username(this.email)
            .password(this.password)
            .roles(this.role)
            .build()
    }

}
