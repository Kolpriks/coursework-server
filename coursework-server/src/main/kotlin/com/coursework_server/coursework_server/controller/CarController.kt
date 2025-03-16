package com.coursework_server.coursework_server.controller

import com.coursework_server.coursework_server.model.Car
import com.coursework_server.coursework_server.repository.CarRepository 
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarController(private val carRepository: CarRepository) {

    // Эндпоинт для вывода всех машин из таблицы cars
    @GetMapping
    fun getAllCars(): List<Car> = carRepository.findAll()

    // Эндпоинт для добавления новой машины
    @PostMapping
    fun createCar(@RequestBody car: Car): ResponseEntity<Car> {
        val savedCar = carRepository.save(car)
        return ResponseEntity(savedCar, HttpStatus.CREATED)
    }
}
