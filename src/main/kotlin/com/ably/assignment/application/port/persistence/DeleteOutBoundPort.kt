package com.ably.assignment.application.port.persistence

import com.ably.assignment.domain.model.User


interface DeleteOutBoundPort {
    fun deleteById(userId: Long)
}