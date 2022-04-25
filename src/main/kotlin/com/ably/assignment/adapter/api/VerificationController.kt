package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.PhoneDto.*
import com.ably.assignment.adapter.api.model.SidDto
import com.ably.assignment.application.port.api.VerificationInBoundPort
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/verification")
class VerificationController(private val verificationInBoundPort: VerificationInBoundPort) {

    @PostMapping("/sms")
    @Operation(description = "SMS 코드를 발급을 합니다.")
    fun sendSms(@RequestBody @Valid phoneVerifyDto: PhoneVerifyingDto): ResponseEntity<String> {
        verificationInBoundPort.sendSms(phoneVerifyDto)
        return ResponseEntity.ok().body("Success: SMS code has been sent.")
    }

    @PostMapping("/sms/{verificationCode}")
    @Operation(description = "/sms에서 발급된 인증코드를 통해 요청합니다. 응답값으로 회원 가입과 비밀번호 재설정에 필요한 SID가 발급됩니다. SID는 Redis에서 5분뒤에 삭제됩니다.")
    fun verifySms(
        @RequestBody @Valid phoneVerifyDto: PhoneVerifyingDto,
        @PathVariable("verificationCode") verificationCode: String,
    ): ResponseEntity<SidDto> {
        val sidDto = verificationInBoundPort.verifySms(phoneVerifyDto, verificationCode)
        return ResponseEntity.ok().body(sidDto)
    }

    @PostMapping("/email")
    fun verifyEmail() {
        verificationInBoundPort.sendEmail()
    }
}