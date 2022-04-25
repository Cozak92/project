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
class UserController(private val userInBoundPort: UserInBoundPort,) {

    @PostMapping
    @Operation(
        description = "필요한 정보를 받아서 회원가입을 진행합니다. \n" +
                "이미 가입된 유저 중에 같은 이메일 또는 같은 전화번호가 존재할시 회원가입이 불가능합니다. \n" +
                "전화번호 인증시 발급된 SID가 http Header에 있어야 회원가입 진행이 가능합니다. \n" +
                "http Header에 휴대폰 번호 인증시 발급된 SID 값이 있어야 진행이 가능합니다. sid = a1111 로 요청할시 sid 검증을 통과 할 수 있습니다.\n"
    )
    fun register(@RequestBody @Valid userSaveDto: UserRegisterDto): ResponseEntity<Response> {
        println(userSaveDto.password)
        val registeredUser = userInBoundPort.register(userSaveDto)
        val response = Response(
            message = "registered successfully",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_CREATED.toString(),
            data = mutableListOf(registeredUser)
        )
        return ResponseEntity.created(URI("/v1/user")).body(response)
    }


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    @Operation(description = "JWT 토큰을 기준으로 회원 정보를 받아옵니다.")
    fun getMyUserInfo(): ResponseEntity<Response> {

        val userInfo = userInBoundPort.getMyUserInfo()

        val response = Response(
            message = "inquired successfully",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(userInfo)
        )
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{userId}")
    @Operation(description = "ADMIN이 User의 ID를 통해 USER의 회원 정보를 확인할때 사용합니다.")
    fun getUserInfo(@PathVariable("userId") userId: Long): ResponseEntity<Response> {
        val userInfo = userInBoundPort.getUserInfo(userId)
        val response = Response(
            message = "inquired successfully",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(userInfo)
        )
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/{userId}")
    @Operation(description = "JWT 토큰을 기준으로 현재 회원의 비밀번호,이메일,휴대폰을 제외한 정보를 수정합니다. 요청의 user id를 통해 얻은 user와 토큰의 정보 user를 교차검증합니다.")
    fun updateMyUserInfo(@PathVariable("userId") userId: Long, @RequestBody @Valid userUpdateDto: UserUpdateDto): ResponseEntity<Response> {
        if(!userInBoundPort.isSameContextUserAsRequestUser(userId)){
            throw IllegalStateException("Token User don't match with Request User")
        }
        val user = userInBoundPort.updateMyUserInfo(userId,userUpdateDto)
        val response = Response(
            message = "modified successfully",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(user)
        )
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PatchMapping("/{userId}/passwd")
    @Operation(description = "JWT 토큰을 기준으로 현재 회원의 비밀번호를 수정합니다. 요청의 user id를 통해 얻은 user와 토큰의 정보 user를 교차검증합니다. http Header에 휴대폰 번호 인증시 발급된 SID 값이 있어야 진행이 가능합니다. sid = a1111 로 요청할시 sid 검증을 통과 할 수 있습니다.")
    fun changeMyPasswd(@PathVariable("userId") userId: Long, @RequestBody @Valid userUpdatePasswdDto: UserUpdatePasswdDto): ResponseEntity<Response> {
        if(!userInBoundPort.isSameContextUserAsRequestUser(userId)){
            throw IllegalStateException("Token User don't match with Request User")
        }
        val user = userInBoundPort.changeMyPasswd(userId,userUpdatePasswdDto)
        val response = Response(
            message = "modified successfully",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.SC_OK.toString(),
            data = mutableListOf(user)
        )
        return ResponseEntity.ok().body(response)
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(description = "JWT 토큰을 기준으로 현재 회원의 탈퇴를 진행합니다. 요청의 user id를 통해 얻은 user와 토큰의 정보 user를 교차검증합니다.")
    fun unregister(@PathVariable("userId") userId: Long) {
        if(!userInBoundPort.isSameContextUserAsRequestUser(userId)){
            throw IllegalStateException("Token User don't match with Request User")
        }
        TODO("Not yet implemented")
    }


}