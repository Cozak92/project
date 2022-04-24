package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*

interface RegisterUserUseCase {
    fun register(userSaveDto: UserRegisterDto): UserResponseDto
}