package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.AuthInBoundPort
import com.ably.assignment.application.usecase.AuthUseCase
import com.ably.assignment.infrastructure.annotations.Adapter

@Adapter
class AuthAdapter(private val authUseCase: AuthUseCase): AuthInBoundPort {
    override fun authorize(userAuthDto: UserAuthDto): UserAuthResponseDto {
        val authorization = authUseCase.doAuthorize(userAuthDto.toDomainModel())
        return authorization.toUserAuthResponseDto()
    }
}