package com.ably.assignment.application.usecase

import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.domain.model.Phone

interface SmsUseCase {
    fun send(phone: Phone)
    fun verify(phone: Phone, verificationCode: String): SidDto
}