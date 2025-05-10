package com.coursework_server.coursework_server.config

import com.coursework_server.coursework_server.model.User
import com.coursework_server.coursework_server.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class DataInitializer {

    @Bean
    fun initUsers(
        userRepository: UserRepository,
        passwordEncoder: PasswordEncoder
    ): CommandLineRunner = CommandLineRunner {
        val managerEmail = "manager@test.com"
        if (userRepository.findByEmail(managerEmail).isEmpty) {
            val manager = User(
                email = managerEmail,
                password = passwordEncoder.encode("password"),
                role = "manager"
            )
            userRepository.save(manager)
            println(">>> Создан менеджер: $managerEmail / password")
        }
    }
}
