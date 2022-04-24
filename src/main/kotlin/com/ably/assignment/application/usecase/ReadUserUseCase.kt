package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*

interface ReadUserUseCase {
    fun readById(userId: Long): UserResponseDto
    fun readByEmail(email: String): UserResponseDto
}