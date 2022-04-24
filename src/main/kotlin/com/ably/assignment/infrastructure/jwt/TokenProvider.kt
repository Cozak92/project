package com.ably.assignment.infrastructure.jwt

import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.token-validity-in-seconds}") private val tokenValidityInSeconds: Long
) {
    private val logger = LoggerFactory.getLogger(TokenProvider::class.java)
    private val AUTHORITIES_KEY = "auth"
    private val tokenValidityInMilliseconds = tokenValidityInSeconds * 1000
    private val key: Key = Decoders.BASE64.decode(secret).let{ keyBytes ->
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun createToken(authentication: Authentication): String {
        val authorities = authentication.authorities.joinToString { obj: GrantedAuthority -> obj.authority }
        val now = Date().time
        val validity = Date(now + tokenValidityInMilliseconds)
        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
        val authorities: Collection<GrantedAuthority> = Arrays.stream(
            claims[AUTHORITIES_KEY].toString().split(",").toTypedArray()
        )
            .map { role: String ->
                println(role)
                SimpleGrantedAuthority(
                    role
                )
            }.toList()
        val principal = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
            logger.info("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
            logger.info("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
            logger.info("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            logger.info("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

}