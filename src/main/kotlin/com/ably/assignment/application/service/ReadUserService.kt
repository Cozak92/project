package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.usecase.ReadUserUseCase
import com.ably.assignment.domain.model.User
import com.ably.assignment.infrastructure.util.SecurityUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ReadUserService(private val readOutBoundPort: ReadOutBoundPort, private val securityUtil: SecurityUtil,): ReadUserUseCase {

    @Transactional(readOnly = true)
    override fun readById(userId: Long): User {
        if (!readOutBoundPort.existsById(userId)){
            throw IllegalArgumentException("User doesn't exists")
        }
        return readOutBoundPort.getUserById(userId)
    }

    @Transactional(readOnly = true)
    override fun readByContext(): User {
        return securityUtil.currentUserEmail?.let { email ->

            if(!readOutBoundPort.existsByEmail(email)){
                throw IllegalArgumentException("User Email doesn't exists")
            }
            return readOutBoundPort.getUserByEmail(email)

        } ?: throw IllegalStateException("user's current email doesn't exist")
    }

}