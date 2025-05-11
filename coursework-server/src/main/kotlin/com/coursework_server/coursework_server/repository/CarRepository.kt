package com.coursework_server.coursework_server.repository

import com.coursework_server.coursework_server.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<Car, Long> {
    @Query("""
        SELECT c
        FROM Car c
        WHERE NOT EXISTS (
            SELECT 1 FROM Assignment a WHERE a.car = c
        )
    """)
    fun findAllUnassigned(): List<Car>
}
