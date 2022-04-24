package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.UserInBoundPort
import com.ably.assignment.application.usecase.ReadUserUseCase
import com.ably.assignment.application.usecase.RegisterUserUseCase
import com.ably.assignment.infrastructure.annotations.Adapter
import com.ably.assignment.infrastructure.util.SecurityUtil

@Adapter
class UserAdapter(
    private val registerUserUseCase: RegisterUserUseCase,
    private val readUserUseCase: ReadUserUseCase,
    private val securityUtil: SecurityUtil,
) : UserInBoundPort {
    override fun register(userSaveDto: UserRegisterDto): UserResponseDto {
        return registerUserUseCase.register(userSaveDto)
    }

    override fun getMyUserInfo(): UserResponseDto {
        return securityUtil.currentUserEmail?.let { email ->
            readUserUseCase.readByEmail(email)
        } ?: throw IllegalStateException("user's current email doesn't exist")
    }

    override fun getUserInfo(userId: Long): UserResponseDto{
        return readUserUseCase.readById(userId)
    }

    override fun updateMyUserInfo() {
        TODO("Not yet implemented")
    }

    override fun updateUserInfo() {
        TODO("Not yet implemented")
    }

    override fun changeMyPasswd() {
        TODO("Not yet implemented")
    }

    override fun unregister() {
        TODO("Not yet implemented")
    }
}