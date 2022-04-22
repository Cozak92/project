package com.ably.assignment.domain.port.inbound

import com.ably.assignment.domain.dto.UserDto


interface UserInBoundPort {
    fun register(userDto: UserDto): UserDto
    fun getMyUserInfo()
    fun getUserInfo()
    fun updateMyUserInfo()
    fun updateUserInfo()
    fun changeMyPasswd()
    fun unregister()
}