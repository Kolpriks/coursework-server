package com.coursework_server.coursework_server.repository

import com.coursework_server.coursework_server.model.Car
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CarRepository : JpaRepository<Car, Long> {
    @Query("""
        select c 
        from Car c 
        where c.id not in (select a.car.id from Assignment a)
    """)
    fun findAllUnassigned(): List<Car>
}
