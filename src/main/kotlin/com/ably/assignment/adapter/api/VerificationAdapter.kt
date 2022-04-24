package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.PhoneDto.*
import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.application.port.api.VerificationInBoundPort
import com.ably.assignment.application.usecase.EmailUseCase
import com.ably.assignment.application.usecase.SmsUseCase
import com.ably.assignment.infrastructure.annotations.Adapter


@Adapter
class VerificationAdapter(private val emailUseCase: EmailUseCase, private val smsUseCase: SmsUseCase) :
    VerificationInBoundPort {
    override fun sendSms(phoneVerifyDto: PhoneVerifyingDto) {
        smsUseCase.send(phoneVerifyDto.toDomainModel())
    }

    override fun verifySms(phoneVerifyDto: PhoneVerifyingDto, verificationCode: String): SidDto {
        return smsUseCase.verify(phoneVerifyDto.toDomainModel(), verificationCode)
    }

    override fun sendEmail() {
        emailUseCase.send()
    }
}