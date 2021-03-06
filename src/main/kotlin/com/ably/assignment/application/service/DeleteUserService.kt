package com.ably.assignment.application.service

import com.ably.assignment.application.port.persistence.DeleteOutBoundPort
import com.ably.assignment.application.usecase.DeleteUserUseCase
import com.ably.assignment.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteUserService(private val deleteOutBoundPort: DeleteOutBoundPort): DeleteUserUseCase {

    @Transactional
    override fun deleteById(userId: Long) {
        deleteOutBoundPort.deleteById(userId)
    }
}