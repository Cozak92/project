package com.ably.assignment.adapter.persistence

import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.domain.model.User

class QuerydslAdapter: ReadOutBoundPort {

    override fun existsByEmail(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun existsById(userId: Long): Boolean {
        TODO("Not yet implemented")
    }


    override fun getUserByEmail(email: String): User {
        TODO("Not yet implemented")
    }

    override fun getUserById(userId: Long): User {
        TODO("Not yet implemented")
    }

    override fun existsByPhone(countryCode: String, number: String): Boolean {
        TODO("Not yet implemented")
    }
}