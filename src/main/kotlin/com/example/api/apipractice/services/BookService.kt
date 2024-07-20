package com.example.api.apipractice.services

import com.example.api.apipractice.model.Books
import com.example.api.apipractice.repository.BookRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BookService(private val bookRepository: BookRepository){
    fun addBook(book: Books): Books {
        return bookRepository.save(book)
    }

    fun updateBook(id: String, book: Books): Books {
        val existingBook=bookRepository.findById(id)
            .orElseThrow{ RuntimeException("Book with ID $id not found") }

        existingBook.author=book.author
        existingBook.genre=book.genre
        existingBook.title=book.title
        existingBook.description=book.description
        existingBook.price=book.price
        existingBook.publishedDate=book.publishedDate

        return bookRepository.save(existingBook)

        //return bookRepository.save(book)
    }

    fun deleteBook(id: String) {
        bookRepository.deleteById(id)
    }

    fun getBookById(id: String): Books? {
        return bookRepository.findById(id).orElse(null)
    }

    fun listAllBooks(): List<Books> {
        return bookRepository.findAll()
    }

    fun searchBooksByTitle(title: String): List<Books> {
        return bookRepository.findByTitleContaining(title)
    }

    fun searchBooksByAuthor(author: String): List<Books> {
        return bookRepository.findByAuthor(author)
    }

    fun searchBooksByGenre(genre: String): List<Books> {
        return bookRepository.findByGenre(genre)
    }
}
