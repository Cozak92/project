package com.ably.assignment.domain.service

import com.ably.assignment.domain.dto.UserDto
import com.ably.assignment.domain.port.outbound.UserOutBoundPort
import org.springframework.stereotype.Service


@Service
class UserService(private val userOutBoundPort:UserOutBoundPort) {

    fun exists(userDto: UserDto): Boolean {
        return userOutBoundPort.findByEmail(userDto.email)?.let{
            true
        } ?: false
    }
}