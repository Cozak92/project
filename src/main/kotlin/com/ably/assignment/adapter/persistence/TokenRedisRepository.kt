package com.ably.assignment.adapter.persistence

import com.ably.assignment.adapter.persistence.model.SidData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenRedisRepository: CrudRepository<SidData, String> {
}