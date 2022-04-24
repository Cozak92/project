package com.ably.assignment.infrastructure.config


import com.ably.assignment.infrastructure.jwt.JwtAccessDeniedHandler
import com.ably.assignment.infrastructure.jwt.JwtAuthenticationEntryPoint
import com.ably.assignment.infrastructure.jwt.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class SecurityConfig(
    private val tokenProvider: TokenProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/api/**", config)
        return CorsFilter(source)
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(
                "/error/**",
                "/authorize/**",
                "/verification/**",
                "/health",
                "/health/**",
                "/swagger-ui.html",
                "/api-docs/**",
                "/swagger-ui/index.html",
                "/actuator/mappings",
                "/swagger-ui/**"
            )
            .antMatchers(HttpMethod.POST, "/user")
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .csrf().disable()
            .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            // 세션을 사용하지 않기 때문에 STATELESS로 설정
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .anyRequest().authenticated()

            .and()
            .apply(JwtSecurityConfig(tokenProvider))
    }
}