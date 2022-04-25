package com.ably.assignment.application.port.persistence

import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.adapter.persistence.model.SidData

interface WriteTokenOutBoundPort {
    fun save(sidData: SidData): SidDto
}