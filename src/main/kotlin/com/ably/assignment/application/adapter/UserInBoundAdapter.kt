package com.ably.assignment.application.adapter

import com.ably.assignment.domain.dto.UserDto
import com.ably.assignment.domain.port.inbound.UserInBoundPort

class UserInBoundAdapter: UserInBoundPort {
    override fun register(userDto: UserDto): UserDto {
        TODO("Not yet implemented")
    }

    override fun getMyUserInfo() {
        TODO("Not yet implemented")
    }

    override fun getUserInfo() {
        TODO("Not yet implemented")
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