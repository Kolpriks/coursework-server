package com.coursework_server.coursework_server.controller

import com.coursework_server.coursework_server.model.Favorite
import com.coursework_server.coursework_server.repository.CarRepository
import com.coursework_server.coursework_server.repository.FavoriteRepository
import com.coursework_server.coursework_server.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/favorites")
class FavoriteController(
    private val favoriteRepository: FavoriteRepository,
    private val userRepository: UserRepository,
    private val carRepository: CarRepository
) {

    @GetMapping
    fun getAllFavorites(): List<Favorite> = favoriteRepository.findAll()

    @GetMapping("/{id}")
    fun getFavoriteById(@PathVariable id: Long): ResponseEntity<Favorite> =
        favoriteRepository.findById(id).map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    // Для создания favorite передаём идентификаторы пользователя и машины в параметрах запроса
    @PostMapping
    fun createFavorite(
        @RequestParam userId: Long,
        @RequestParam carId: Long
    ): ResponseEntity<Favorite> {
        val userOpt = userRepository.findById(userId)
        val carOpt = carRepository.findById(carId)
        return if (userOpt.isPresent && carOpt.isPresent) {
            val favorite = Favorite(user = userOpt.get(), car = carOpt.get())
            ResponseEntity(favoriteRepository.save(favorite), HttpStatus.CREATED)
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteFavorite(@PathVariable id: Long): ResponseEntity<Void> {
        return if (favoriteRepository.existsById(id)) {
            favoriteRepository.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
