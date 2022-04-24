package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.Response
import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.UserInBoundPort
import io.swagger.v3.oas.annotations.Operation
import org.apache.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime
import javax.validation.Valid


@RestController
@RequestMapping("/user")
class UserController(private val userInBoundPort: UserInBoundPort) {

    @PostMapping
    @Operation(description = "필요한 정보를 받아서 회원가입을 진행합니다. " +
            "이미 가입된 유저 중에 같은 이메일 또는 같은 전화번호가 존재할시 회원가입이 불가능합니다. " +
            "전화번호 인증시 발급된 SID가 http Header에 있어야 회원가입 진행이 가능합니다.")
    fun register(@RequestBody @Valid userSaveDto: UserRegisterDto): ResponseEntity<Response> {
        val registeredUser = userInBoundPort.register(userSaveDto)
        val response = Response(message = "User registration succeeded",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_CREATED.toString(),
            data = mutableListOf(registeredUser))
        return ResponseEntity.created(URI("/v1/user")).body(response)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/admin")
    @Operation(description = "ADMIN 회원 가입 기능입니다. ADMIN만 다른 ADMIN의 회원가입 요청을 보낼 수 있습니다.")
    fun registerAdmin(@RequestBody @Valid userSaveDto: UserRegisterDto): ResponseEntity<Response> {
        val registeredUser = userInBoundPort.register(userSaveDto)
        val response = Response(message = "Admin registration succeeded",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_CREATED.toString(),
            data = mutableListOf(registeredUser))
        return ResponseEntity.created(URI("/v1/user")).body(response)
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    @Operation(description = "JWT 토큰을 기준으로 회원 정보를 받아옵니다.")
    fun getMyUserInfo(): ResponseEntity<Response> {
        val userInfo = userInBoundPort.getMyUserInfo()
        val response = Response(message = "User inquiry succeeded",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(userInfo))
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    @Operation(description = "ADMIN이 User의 ID를 통해 USER의 회원 정보를 확인할때 사용합니다.")
    fun getUserInfo( @PathVariable("userId") userId: Long):  ResponseEntity<Response> {
        val userInfo = userInBoundPort.getUserInfo(userId)
        val response = Response(message = "User inquiry succeeded",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(userInfo))
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping
    @Operation(description = "JWT 토큰을 기준으로 현재 회원정보를 수정합니다.")
    fun updateMyUserInfo() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/passwd")
    @Operation(description = "회원의 비밀번호를 수정합니다. 회원 가입과 마찬가지로 http Header에 휴대폰 번호 인증시 발급된 SID 값이 있어야 진행이 가능합니다.")
    fun changeMyPasswd() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping
    fun unregister() {
        TODO("Not yet implemented")
    }


}