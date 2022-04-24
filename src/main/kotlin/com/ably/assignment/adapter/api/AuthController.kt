package com.ably.assignment.adapter.api

import com.ably.assignment.adapter.api.model.UserDto.*
import com.ably.assignment.application.port.api.AuthInBoundPort
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/authorize")
class AuthController(private val authInBoundPort: AuthInBoundPort) {

    @PostMapping
    @Operation(description = "이메일과 비밀번호를 받아서 로그인을 진행하고 토큰을 발급합니다.")
    fun authorize(@RequestBody @Valid userAuthDto: UserAuthDto):ResponseEntity<UserTokenDto> {
        val token = authInBoundPort.authorize(userAuthDto)
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Authorization", "Bearer ${token.value}")
        return ResponseEntity.created(URI("/authorize"))
            .headers(httpHeaders)
            .body(token)
    }

    @DeleteMapping
    @Operation(description = "로그아웃 처리는 구현 복잡도로 인해 시간 관계상 구현하지 못했습니다 😥")
    fun unauthorize(){
        TODO("Not yet implemented")
    }
}