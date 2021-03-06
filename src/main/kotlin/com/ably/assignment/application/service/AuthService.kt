package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.usecase.AuthUseCase
import com.ably.assignment.domain.vo.Authorization
import com.ably.assignment.infrastructure.jwt.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthService(private val tokenProvider: TokenProvider,
                  private val authenticationManagerBuilder: AuthenticationManagerBuilder
):AuthUseCase {
    override fun doAuthorize(authorization: Authorization): Authorization {
        val authenticationToken = UsernamePasswordAuthenticationToken(authorization.email, authorization.password)
        val authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication)
        authorization.token = jwt
        return authorization
    }
}