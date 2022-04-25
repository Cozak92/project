package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.domain.model.User

interface ReadUserUseCase {
    fun readById(userId: Long): User
    fun readByContext(): User
}