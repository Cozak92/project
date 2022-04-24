package com.ably.assignment.application.port.api

import com.ably.assignment.adapter.api.model.PhoneDto.*
import com.ably.assignment.adapter.api.model.SidDto

interface VerificationInBoundPort {
    fun sendSms(phoneVerifyDto: PhoneVerifyingDto)
    fun verifySms(phoneVerifyDto: PhoneVerifyingDto, verificationCode: String): SidDto
    fun sendEmail()
}