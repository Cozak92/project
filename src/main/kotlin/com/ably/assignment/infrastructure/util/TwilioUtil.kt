package com.ably.assignment.infrastructure.util

import com.twilio.Twilio
import com.twilio.rest.verify.v2.service.Verification
import com.twilio.rest.verify.v2.service.VerificationCheck
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TwilioUtil(
    @Value("\${twilio.account_sid}")
    private val ACCOUNT_SID: String,

    @Value("\${twilio.auth_token}")
    private val AUTH_TOKEN: String,

    @Value("\${twilio.from_number}")
    private val FROM_NUMBER: String,

    @Value("\${twilio.service_sid}")
    private val SERVICE_SID: String,
) {

    init {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN)
    }

    fun sendSms(phoneNumber: String) {
        val verification = Verification.creator(
            SERVICE_SID,
            phoneNumber,
            "sms")
            .create()
        if (verification.status == "canceled") {
            throw IllegalStateException("SMS Sending is failed")
        }
    }

    fun checkCode(phoneNumber: String, verificationCode: String): List<String> {
        val verificationCheck: VerificationCheck = VerificationCheck.creator(
            SERVICE_SID,
            verificationCode)
            .setTo(phoneNumber).create()
        if (verificationCheck.status != "approved") {
            throw IllegalStateException("Verfication failed")
        }
        return listOf(verificationCheck.sid, verificationCheck.to)
    }
}