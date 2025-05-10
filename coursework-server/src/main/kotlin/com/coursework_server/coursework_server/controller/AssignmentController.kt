package com.coursework_server.coursework_server.controller

import com.coursework_server.coursework_server.model.Assignment
import com.coursework_server.coursework_server.repository.AssignmentRepository
import com.coursework_server.coursework_server.repository.CarRepository
import com.coursework_server.coursework_server.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/assignments")
class AssignmentController(
    private val assignmentRepository: AssignmentRepository,
    private val userRepository: UserRepository,
    private val carRepository: CarRepository
) {

    @PostMapping
    fun assignCarToUser(
        @RequestParam carId: Long,
        @RequestParam email: String
    ): ResponseEntity<String> {
        // найти пользователя по email
        val userOpt = userRepository.findByEmail(email)
        if (userOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Пользователь с email '$email' не найден")
        }
        val user = userOpt.get()

        // найти машину по id
        val carOpt = carRepository.findById(carId)
        if (carOpt.isEmpty) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Машина с id=$carId не найдена")
        }
        val car = carOpt.get()

        // проверить существующую связь
        if (assignmentRepository.existsByUserAndCar(user, car)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Пользователь уже назначен на эту машину")
        }

        // создать связь
        val assignment = Assignment(user = user, car = car)
        assignmentRepository.save(assignment)
        return ResponseEntity.ok("Машина успешно назначена пользователю")
    }
}
