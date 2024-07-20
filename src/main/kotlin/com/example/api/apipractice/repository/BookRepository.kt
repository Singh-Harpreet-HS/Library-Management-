package com.example.api.apipractice.repository

import com.example.api.apipractice.model.Books
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: MongoRepository<Books, String> {
    fun findByTitleContaining(title: String): List<Books>
    fun findByAuthor(author: String): List<Books>
    fun findByGenre(genre: String): List<Books>
}