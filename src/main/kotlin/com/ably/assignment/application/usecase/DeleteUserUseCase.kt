package com.ably.assignment.application.usecase

import com.ably.assignment.domain.model.User

interface DeleteUserUseCase {
    fun deleteById(userId: Long)
}