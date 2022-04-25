package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.domain.vo.Authorization

interface AuthUseCase {
    fun doAuthorize(authorization: Authorization): Authorization
}