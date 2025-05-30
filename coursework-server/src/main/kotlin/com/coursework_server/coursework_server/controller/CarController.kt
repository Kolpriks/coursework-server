package com.coursework_server.coursework_server.controller

import com.coursework_server.coursework_server.model.Car
import com.coursework_server.coursework_server.repository.CarRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cars")
class CarController(private val carRepository: CarRepository) {

    @GetMapping
    fun getAllCars(): List<Car> = carRepository.findAll()

    @GetMapping("/{id}")
    fun getCarById(@PathVariable id: Long): ResponseEntity<Car> {
        val carOptional = carRepository.findById(id)
        return if (carOptional.isPresent) {
            ResponseEntity.ok(carOptional.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createCar(@RequestBody car: Car): ResponseEntity<Car> =
        ResponseEntity(carRepository.save(car), HttpStatus.CREATED)

    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long, @RequestBody updatedCar: Car): ResponseEntity<Car> {
        return if (carRepository.existsById(id)) {
            val carToUpdate = updatedCar.copy(id = id)
            ResponseEntity.ok(carRepository.save(carToUpdate))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Void> {
        return if (carRepository.existsById(id)) {
            carRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
