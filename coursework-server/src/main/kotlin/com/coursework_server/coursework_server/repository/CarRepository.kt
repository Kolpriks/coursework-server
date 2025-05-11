package com.coursework_server.coursework_server.repository

import com.coursework_server.coursework_server.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<Car, Long> {
    /**
     * Возвращает все автомобили, которые не назначены ни одному пользователю
     */
    @Query("""
        SELECT c
        FROM Car c
        LEFT JOIN Assignment a ON a.car = c
        WHERE a.id IS NULL
    """)
    fun findAllUnassigned(): List<Car>
}
