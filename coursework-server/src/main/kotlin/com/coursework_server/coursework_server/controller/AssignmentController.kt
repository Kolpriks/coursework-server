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

    /**
     * Эндпоинт назначения машины покупателю менеджером
     */
    @PostMapping
    fun assignCarToUser(
        @RequestParam carId: Long,
        @RequestParam buyerEmail: String,
        @RequestParam managerEmail: String
    ): ResponseEntity<String> {
        // Получение покупателя
        val buyer = userRepository.findByEmail(buyerEmail)
            .orElseThrow { IllegalArgumentException("Покупатель с email='$buyerEmail' не найден") }

        // Получение менеджера и проверка роли
        val manager = userRepository.findByEmail(managerEmail)
            .orElseThrow { IllegalArgumentException("Менеджер с email='$managerEmail' не найден") }
        if (manager.role != "manager") {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Пользователь $managerEmail не является менеджером")
        }

        // Получение машины
        val car = carRepository.findById(carId)
            .orElseThrow { IllegalArgumentException("Машина с id=$carId не найдена") }

        // Проверка существующей связи
        if (assignmentRepository.existsByUserAndCar(buyer, car)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Покупатель уже назначен на эту машину")
        }

        // Создание и сохранение записи
        val assignment = Assignment(user = buyer, car = car, manager = manager)
        assignmentRepository.save(assignment)
        return ResponseEntity.ok("Машина успешно назначена покупателю менеджером $managerEmail")
    }

    /**
     * Эндпоинт: количество назначений по менеджеру
     */
    @GetMapping("/count")
    fun getCountByManager(
        @RequestParam managerEmail: String
    ): ResponseEntity<Map<String, Any>> {
        val manager = userRepository.findByEmail(managerEmail)
            .orElseThrow { IllegalArgumentException("Менеджер с email='$managerEmail' не найден") }
        val count = assignmentRepository.countByManager(manager)
        return ResponseEntity.ok(
            mapOf(
                "managerEmail" to managerEmail,
                "assignmentsCount" to count
            )
        )
    }

    /**
     * Эндпоинт: список назначений по менеджеру
     */
    @GetMapping
    fun getAssignmentsByManager(
        @RequestParam managerEmail: String
    ): ResponseEntity<List<Assignment>> {
        val manager = userRepository.findByEmail(managerEmail)
            .orElseThrow { IllegalArgumentException("Менеджер с email='$managerEmail' не найден") }
        val list = assignmentRepository.findAllByManager(manager)
        return ResponseEntity.ok(list)
    }
}
