package com.ably.assignment.application.adapter

import com.ably.assignment.application.jwt.TokenProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.web.bind.annotation.PostMapping

class AuthController(private val tokenProvider: TokenProvider,
                     private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

    @PostMapping("/authorize")
    fun authorize() {
        TODO("Not yet implemented")
    }
}