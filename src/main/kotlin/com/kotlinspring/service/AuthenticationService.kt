package com.kotlinspring.service

import com.kotlinspring.config.JwtProperties
import com.kotlinspring.controller.auth.AuthenticationRequest
import com.kotlinspring.controller.auth.AuthenticationResponse
import com.kotlinspring.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun authentication(authRequest: AuthenticationRequest): AuthenticationResponse {
        authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password
            )
        )

        val user = userDetailsService.loadUserByUsername(authRequest.email)

        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        refreshTokenRepository.save(refreshToken, user)

        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
            val extractedEmail = tokenService.extractEmail(refreshToken)

            return extractedEmail?.let { email ->
                val currentUserDetails = userDetailsService.loadUserByUsername(email)
                val refreshTokenUserDetails = refreshTokenRepository.findUserByToken(refreshToken)
                if (!tokenService.isExpired(refreshToken) && refreshTokenUserDetails?.username == currentUserDetails.username){
                    generateAccessToken(currentUserDetails)
                }else{
                    null
                }
            }
    }

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )

    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )
}