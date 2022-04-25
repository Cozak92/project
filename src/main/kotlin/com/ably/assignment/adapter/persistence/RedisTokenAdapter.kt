package com.ably.assignment.adapter.persistence

import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.adapter.persistence.model.SidData
import com.ably.assignment.application.port.persistence.ReadTokenOutBoundPort
import com.ably.assignment.application.port.persistence.WriteTokenOutBoundPort
import com.ably.assignment.infrastructure.annotations.Adapter
import org.springframework.data.repository.findByIdOrNull

@Adapter
class RedisTokenAdapter(private val tokenRedisRepository:TokenRedisRepository): WriteTokenOutBoundPort, ReadTokenOutBoundPort {
    override fun save(sidData: SidData): SidDto {
        return tokenRedisRepository.save(sidData).toSidDto()
    }

    override fun sidExists(sid: String): Boolean {
        return tokenRedisRepository.findByIdOrNull(sid)?.let {
            true
        } ?: false
    }
}