package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.application.usecase.RegisterUserUseCase
import com.ably.assignment.domain.vo.Authority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class RegisterUserService(private val writeOutBoundPort: WriteOutBoundPort, private val readOutBoundPort:ReadOutBoundPort, private val passwordEncoder: PasswordEncoder) :
    RegisterUserUseCase {

    @Transactional
    override fun register(userSaveDto: UserRegisterDto): UserResponseDto {
        val newUser = userSaveDto.toDomainModel(Authority.USER)
        newUser.passwordEncoder = passwordEncoder

        newUser.phone.isValid()
        newUser.encryptPassword()

        if(readOutBoundPort.existsByEmail(newUser.information.email)){
            throw IllegalArgumentException("User Email already exists")
        }
        if (readOutBoundPort.existsByPhone(newUser.phone.countryCode, newUser.phone.numberLine)){
            throw IllegalArgumentException("User phone number already exists")
        }

        return writeOutBoundPort.save(newUser).toResponseDto()
    }

}