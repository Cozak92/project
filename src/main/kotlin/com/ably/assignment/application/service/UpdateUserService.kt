package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.application.usecase.UpdateUserUseCase
import com.ably.assignment.domain.model.Phone
import com.ably.assignment.domain.model.User
import com.ably.assignment.domain.vo.FullName
import com.ably.assignment.domain.vo.Information
import com.ably.assignment.infrastructure.util.SecurityUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class UpdateUserService(private val writeOutBoundPort: WriteOutBoundPort,private val readOutBoundPort: ReadOutBoundPort, private val passwordEncoder: PasswordEncoder) :
    UpdateUserUseCase {

    @Transactional
    override fun updateUserInfo(user: User): User {

        return  writeOutBoundPort.updateUserInfo(user)
    }

    @Transactional
    override fun updateUserPasswd(user: User): User {

        user.passwordEncoder = passwordEncoder
        user.encryptPassword()


        return writeOutBoundPort.updateUserPasswd(user)
    }
}


