package com.example.api.apipractice.repository

import com.example.api.apipractice.model.Orders
import org.springframework.data.mongodb.repository.MongoRepository

interface OrderRepository: MongoRepository<Orders,String> {
    fun findByUserId(userId: String): List<Orders>
}