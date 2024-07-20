package com.example.api.apipractice.repository

import com.example.api.apipractice.model.Users
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository: MongoRepository<Users, String> {
    fun findByUsername(username: String): Optional<Users>
}