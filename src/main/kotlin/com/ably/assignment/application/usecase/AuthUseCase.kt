package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*

interface AuthUseCase {
    fun doAuthorize(userAuthDto:UserAuthDto): UserTokenDto
}