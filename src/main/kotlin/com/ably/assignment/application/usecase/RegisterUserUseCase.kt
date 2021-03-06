package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.domain.model.User

interface RegisterUserUseCase {
    fun register(user: User): User
}