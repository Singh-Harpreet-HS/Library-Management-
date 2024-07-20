package com.example.api.apipractice.repository

import com.example.api.apipractice.model.Reviews
import org.springframework.data.mongodb.repository.MongoRepository

interface ReviewRepository: MongoRepository<Reviews, String> {
    fun findByBookId(bookId: String): List<Reviews>
    fun findByUserId(userId: String): List<Reviews>
}