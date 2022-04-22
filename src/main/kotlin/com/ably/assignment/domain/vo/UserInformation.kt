package com.ably.assignment.domain.vo

import javax.persistence.Column
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

//이메일
//닉네임
//비밀번호
//이름
//전화번호

data class UserInformation(
    @Column(nullable = false, length = 200)
    val email: String,
    @Column(nullable = false, length = 100)
    val nickname: String,
    @Column(nullable = false, length = 50)
    val name: String,
    @Column(nullable = false, length = 20)
    val phoneNationalCode: String,
    @Column(nullable = false)
    val phoneNumber: Int
)