package com.coursework_server.coursework_server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    // По-прежнему нужен бином для шифрования паролей, если где-то ещё используется
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            // Разрешаем абсолютно все запросы без аутентификации
            .authorizeHttpRequests { auth ->
                auth.anyRequest().permitAll()
            }
            // Отключаем HTTP Basic (если не нужен вовсе), иначе можно оставить
            .httpBasic(Customizer.withDefaults())

        return http.build()
    }
}
