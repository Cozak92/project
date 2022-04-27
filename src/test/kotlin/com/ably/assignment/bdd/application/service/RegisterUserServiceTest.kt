package com.ably.assignment.bdd.application.service


import com.ably.assignment.application.port.persistence.ReadOutBoundPort
import com.ably.assignment.application.port.persistence.WriteOutBoundPort
import com.ably.assignment.application.service.RegisterUserService
import com.ably.assignment.domain.model.Phone
import com.ably.assignment.domain.model.User
import com.ably.assignment.domain.vo.FullName
import com.ably.assignment.domain.vo.Information
import com.google.i18n.phonenumbers.NumberParseException
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldEndWith
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.verify
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ContextConfiguration


@ContextConfiguration(classes = [(RegisterUserService::class)])
class RegisterUserServiceTest: BehaviorSpec() {
    override fun extensions() = listOf(SpringExtension)

    @MockkBean
    lateinit var writeOutBoundPort: WriteOutBoundPort
    @MockkBean
    lateinit var readOutBoundPort: ReadOutBoundPort
    @MockkBean
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var registerUserService: RegisterUserService

    init {
        given("가입 요청한 유저가 들어왔을 때"){
            var user: User = User(
                fullName = FullName("Seung", "Shin"),
                password = "sss",
                phone = Phone("+82", "1064114622"),
                information = Information(email = "example@example.com", nickname = "nickname"),
            )

            `when`("휴대폰 번호가 올바르지 않다면") {
                user = user.apply {
                    phone = Phone(countryCode = "czxvzxcv", numberLine = "34535")
                }
                every { readOutBoundPort.existsByEmail(user.information!!.email!!) } returns false
                every { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) } returns false
                every { passwordEncoder.encode(any()) } returns "zzxckvzxckv"
                every { writeOutBoundPort.save(user) } returns user


                then("NumberParseException 예외를 반환한다."){

                    val result = shouldThrowExactly<NumberParseException> {
                        registerUserService.register(user)
                    }
                    verify(exactly = 0) { readOutBoundPort.existsByEmail(user.information!!.email!!) }
                    verify(exactly = 0) { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) }
                    verify(exactly = 0) { writeOutBoundPort.save(user) }

                    result.message.shouldNotBe(null)

                }
            }
            `when`("유저의 Email이 존재 한다면"){
                user = user.apply {
                    phone = Phone(countryCode = "+82", numberLine = "1064114622")
                    information = Information(email = "dup@dup.com", nickname = "nickname")
                }
                every { readOutBoundPort.existsByEmail(user.information!!.email!!) } returns true
                every { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) } returns false
                every { passwordEncoder.encode(any()) } returns "zzxckvzxckv"
                every { writeOutBoundPort.save(user) } returns user


                then("IllegalArgumentException 예외를 반환한다."){
                    val result = shouldThrowExactly<IllegalArgumentException> {
                        registerUserService.register(user)
                    }
                    verify(exactly = 1) { readOutBoundPort.existsByEmail(user.information!!.email!!) }
                    verify(exactly = 0) { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) }
                    verify(exactly = 0) { writeOutBoundPort.save(user) }

                    result.message.shouldNotBe(null)
                    result.message.shouldBe("User Email already exists")
                }
            }
            `when`("유저의 휴대폰번호가 존재 한다면"){
                user = user.apply {
                    phone = Phone(countryCode = "+82", numberLine = "1064114622")
                }
                every { readOutBoundPort.existsByEmail(user.information!!.email!!) } returns false
                every { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) } returns true
                every { passwordEncoder.encode(any()) } returns "zzxckvzxckv"
                every { writeOutBoundPort.save(user) } returns user


                then("IllegalArgumentException 예외를 반환한다."){
                    val result = shouldThrowExactly<IllegalArgumentException> {
                        registerUserService.register(user)
                    }
                    verify(exactly = 1) { readOutBoundPort.existsByEmail(user.information!!.email!!) }
                    verify(exactly = 1) { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) }
                    verify(exactly = 0) { writeOutBoundPort.save(user) }

                    result.message.shouldNotBe(null)
                    result.message.shouldBe("User phone number already exists")

                }
            }
            `when`("유저의 정보가 정상적이라면"){
                every { readOutBoundPort.existsByEmail(user.information!!.email!!) } returns false
                every { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) } returns false
                every { passwordEncoder.encode(any()) } returns "zzxckvzxckv"
                every { writeOutBoundPort.save(user) } returns user

                then("writeOutBoundPort 호출해 유저를 저장한다."){
                    every { writeOutBoundPort.save(user) } returns user

                    val result = registerUserService.register(user)

                    verify(exactly = 1) { readOutBoundPort.existsByEmail(user.information!!.email!!) }
                    verify(exactly = 1) { readOutBoundPort.existsByPhone(user.phone!!.countryCode, user.phone!!.numberLine) }
                    verify(exactly = 1) { writeOutBoundPort.save(user) }

                    result shouldNotBe null
                    result shouldBe user
                    result shouldBeSameInstanceAs user



                }
            }
        }
    }
}