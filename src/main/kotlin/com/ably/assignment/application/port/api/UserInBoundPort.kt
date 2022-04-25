package com.ably.assignment.application.port.api

import com.ably.assignment.adapter.api.model.UserDto.*


interface UserInBoundPort {
    fun register(userRegisterDto: UserRegisterDto): UserResponseDto
    fun getMyUserInfo(): UserResponseDto
    fun getUserInfo(userId: Long): UserResponseDto
    fun updateMyUserInfo(userId: Long, userUpdateDto: UserUpdateDto): UserResponseDto
    fun changeMyPasswd(userId: Long, userUpdatePasswdDto: UserUpdatePasswdDto): UserResponseDto
    fun unregister(userId: Long): UserResponseDto
    fun isSameContextUserAsRequestUser(userId: Long): Boolean
}