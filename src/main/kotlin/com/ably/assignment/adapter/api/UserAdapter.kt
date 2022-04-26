package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.UserInBoundPort
import com.ably.assignment.application.usecase.ReadUserUseCase
import com.ably.assignment.application.usecase.RegisterUserUseCase
import com.ably.assignment.application.usecase.UpdateUserUseCase
import com.ably.assignment.domain.vo.Authority
import com.ably.assignment.infrastructure.annotations.Adapter

@Adapter
class UserAdapter(
    private val registerUserUseCase: RegisterUserUseCase,
    private val readUserUseCase: ReadUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase

) : UserInBoundPort {
    override fun register(userRegisterDto: UserRegisterDto): UserResponseDto {
        val user = userRegisterDto.toDomainModel(Authority.USER)
        val registeredUser = registerUserUseCase.register(user)
        return registeredUser.toResponseDto()
    }

    override fun getMyUserInfo(): UserResponseDto {
        val inquiredUser = readUserUseCase.readByContext()
        return inquiredUser.toResponseDto()
    }

    override fun getUserInfo(userId: Long): UserResponseDto{
        val inquiredUser = readUserUseCase.readById(userId)
        return inquiredUser.toResponseDto()
    }

    override fun updateMyUserInfo(userId: Long, userUpdateDto: UserUpdateDto): UserResponseDto {
        val user = userUpdateDto.toDomainModel(Authority.USER)
        user.id = userId
        val updatedUser = updateUserUseCase.updateUserInfo(user)
        return updatedUser.toResponseDto()
    }

    override fun changeMyPasswd(userId: Long, userUpdatePasswdDto: UserUpdatePasswdDto):UserResponseDto {
        if (userUpdatePasswdDto.firstPassword != userUpdatePasswdDto.secondPassword) {
            throw IllegalStateException("Passwords doesn't match")
        }

        val user = userUpdatePasswdDto.toDomainModel()
        user.id = userId
        val passwdChangedUser = updateUserUseCase.updateUserPasswd(user)

        return passwdChangedUser.toResponseDto()
    }

    override fun unregister(userId: Long): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun isSameContextUserAsRequestUser(userId: Long): Boolean {
        val requestUser = readUserUseCase.readById(userId)
        val contextUser = readUserUseCase.readByContext()

        if(requestUser.id != contextUser.id){
            return false
        }
        return true
    }
}