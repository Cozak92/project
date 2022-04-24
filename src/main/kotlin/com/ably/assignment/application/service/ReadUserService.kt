package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.usecase.ReadUserUseCase
import org.springframework.stereotype.Service


@Service
class ReadUserService(private val readOutBoundPort: ReadOutBoundPort): ReadUserUseCase {
    override fun readById(userId: Long): UserResponseDto {
        if (!readOutBoundPort.existsById(userId)){
            throw IllegalArgumentException("User doesn't exists")
        }
        return readOutBoundPort.getUserById(userId).toResponseDto()
    }

    override fun readByEmail(email: String): UserResponseDto {
        if(!readOutBoundPort.existsByEmail(email)){
            throw IllegalArgumentException("User Email doesn't exists")
        }
        return readOutBoundPort.getUserByEmail(email).toResponseDto()
    }

}