package com.ably.assignment.application.adapter

import com.ably.assignment.domain.dto.UserDto
import com.ably.assignment.domain.port.inbound.UserInBoundPort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/user")
class UserController(private val userInBoundPort: UserInBoundPort) {

    @PostMapping
    fun register(@RequestBody @Valid userDto: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity.ok(userInBoundPort.register(userDto))
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    fun getMyUserInfo() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getUserInfo() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('USER')")
    fun updateMyUserInfo() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('USER')")
    fun changeMyPasswd() {
        TODO("Not yet implemented")
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    fun unregister() {
        TODO("Not yet implemented")
    }


}