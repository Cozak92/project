package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.application.usecase.RegisterUserUseCase
import com.ably.assignment.domain.model.User
import com.ably.assignment.domain.vo.Authority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class RegisterUserService(private val writeOutBoundPort: WriteOutBoundPort, private val readOutBoundPort:ReadOutBoundPort, private val passwordEncoder: PasswordEncoder) :
    RegisterUserUseCase {

    @Transactional
    override fun register(user: User): User {
        user.passwordEncoder = passwordEncoder

        user.phone!!.isValid()
        user.encryptPassword()

        if(readOutBoundPort.existsByEmail(user.information!!.email!!)){
            throw IllegalArgumentException("User Email already exists")
        }
        if (readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine)){
            throw IllegalArgumentException("User phone number already exists")
        }

        return writeOutBoundPort.save(user)
    }

}