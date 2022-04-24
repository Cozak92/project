package com.ably.assignment.application.port.api

import com.ably.assignment.adapter.api.model.UserDto.*


interface UserInBoundPort {
    fun register(userSaveDto: UserRegisterDto): UserResponseDto
    fun getMyUserInfo(): UserResponseDto
    fun getUserInfo(userId :Long): UserResponseDto
    fun updateMyUserInfo()
    fun updateUserInfo()
    fun changeMyPasswd()
    fun unregister()
}