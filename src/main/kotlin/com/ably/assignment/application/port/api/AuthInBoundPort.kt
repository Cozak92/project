package com.ably.assignment.application.port.api

import com.ably.assignment.adapter.api.model.UserDto.*

interface AuthInBoundPort {
    fun authorize(userAuthDto: UserAuthDto):UserAuthResponseDto
}