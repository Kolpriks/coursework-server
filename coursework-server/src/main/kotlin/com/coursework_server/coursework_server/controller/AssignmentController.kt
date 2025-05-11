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
        @RequestParam buyerEmail: String,
        @RequestParam managerEmail: String
    ): ResponseEntity<String> {
        // Получение покупателя
        val buyerOpt = userRepository.findByEmail(buyerEmail)
        if (buyerOpt.isEmpty) {
            return ResponseEntity.badRequest()
                .body("Покупатель с email='$buyerEmail' не найден")
        }
        val buyer = buyerOpt.get()

        // Получение менеджера
        val managerOpt = userRepository.findByEmail(managerEmail)
        if (managerOpt.isEmpty) {
            return ResponseEntity.badRequest()
                .body("Менеджер с email='$managerEmail' не найден")
        }
        val manager = managerOpt.get()

        // Проверка роли менеджера
        if (manager.role != "manager") {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Пользователь $managerEmail не является менеджером")
        }

        // Получение машины
        val carOpt = carRepository.findById(carId)
        if (carOpt.isEmpty) {
            return ResponseEntity.badRequest()
                .body("Машина с id=$carId не найдена")
        }
        val car = carOpt.get()

        // Проверка существующей связи
        if (assignmentRepository.existsByUserAndCar(buyer, car)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Покупатель уже назначен на эту машину")
        }

        // Создание записи
        val assignment = Assignment(user = buyer, car = car, manager = manager)
        assignmentRepository.save(assignment)
        return ResponseEntity.ok("Машина успешно назначена покупателю менеджером $managerEmail")
    }

    @GetMapping("/count")
    fun getCountByManager(
        @RequestParam managerEmail: String
    ): ResponseEntity<Map<String, Long>> {
        val managerOpt = userRepository.findByEmail(managerEmail)
        if (managerOpt.isEmpty) {
            return ResponseEntity.badRequest().build()
        }
        val manager = managerOpt.get()
        val count = assignmentRepository.countByManager(manager)
        return ResponseEntity.ok(mapOf("manager" to managerEmail, "assignmentsCount" to count))
    }

    @GetMapping
    fun getAssignmentsByManager(
        @RequestParam managerEmail: String
    ): ResponseEntity<List<Assignment>> {
        val managerOpt = userRepository.findByEmail(managerEmail)
        if (managerOpt.isEmpty) return ResponseEntity.badRequest().build()
        val manager = managerOpt.get()
        val list = assignmentRepository.findAllByManager(manager)
        return ResponseEntity.ok(list)
    }
}
