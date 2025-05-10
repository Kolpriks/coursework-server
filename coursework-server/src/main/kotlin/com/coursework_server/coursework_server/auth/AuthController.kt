package com.coursework_server.coursework_server.auth

import com.coursework_server.coursework_server.model.User
import com.coursework_server.coursework_server.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ResponseEntity<UserResponse> {
        if (userRepository.findByEmail(req.email).isPresent) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
        val user = User(
            email = req.email,
            password = passwordEncoder.encode(req.password),
            role = "user"
        )
        val saved = userRepository.save(user)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UserResponse(saved.id, saved.email, saved.role))
    }

    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ResponseEntity<UserResponse> {
        val userOpt = userRepository.findByEmail(req.email)
        if (userOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        val user = userOpt.get()
        if (!passwordEncoder.matches(req.password, user.password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(UserResponse(user.id, user.email, user.role))
    }
}
