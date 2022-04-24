package com.ably.assignment.application.service

import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.adapter.persistence.model.SidData
import com.ably.assignment.application.port.persistence.WriteTokenOutBoundPort
import com.ably.assignment.application.usecase.SmsUseCase
import com.ably.assignment.domain.model.Phone
import com.ably.assignment.infrastructure.util.TwilioUtil
import com.twilio.Twilio
import com.twilio.rest.verify.v2.service.Verification
import com.twilio.rest.verify.v2.service.VerificationCheck
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class SmsService(
    private val writeTokenOutBoundPort: WriteTokenOutBoundPort,
    private val twilioUtil: TwilioUtil,
) : SmsUseCase {


    override fun send(phone: Phone) {
        twilioUtil.sendSms(phone.number)
    }

    @Transactional
    override fun verify(phone: Phone, verificationCode: String): SidDto {
        val (sid, phoneNumber) = twilioUtil.checkCode(phone.number, verificationCode)
        writeTokenOutBoundPort.save(SidData(id = sid, value = phoneNumber))
        return SidDto(sid)
    }


}