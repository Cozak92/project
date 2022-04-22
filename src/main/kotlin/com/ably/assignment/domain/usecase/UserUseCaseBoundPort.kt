package com.ably.assignment.domain.usecase

import com.ably.assignment.domain.dto.UserDto
import com.ably.assignment.domain.port.inbound.UserInBoundPort
import com.ably.assignment.domain.port.outbound.UserOutBoundPort
import com.ably.assignment.domain.service.UserService
import com.ably.assignment.mapper.UserMapper
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class UserUseCaseBoundPort(private val userOutBoundPort: UserOutBoundPort, private val userService: UserService): UserInBoundPort {

    @Transactional
    override fun register(userDto: UserDto): UserDto {
        val mapper = Mappers.getMapper(UserMapper::class.java)
        if(userService.exists(userDto)){
            throw IllegalAccessException("user Email already exists : ${userDto.email}")
        }
        return userOutBoundPort.save(mapper.convertToEntity(userDto)).let {
            mapper.convertToDto(it)
        }
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