package com.example.api.apipractice.controllers

import com.example.api.apipractice.model.Books
import com.example.api.apipractice.services.BookService
import org.springframework.http.HttpOutputMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/books")
class BookController(private val bookService: BookService) {

    @PostMapping
    fun addBook(@RequestBody book: Books): ResponseEntity<Books> {
        return ResponseEntity.ok(bookService.addBook(book))
    }

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: String, @RequestBody book: Books): ResponseEntity<Books> {
        return ResponseEntity.ok(bookService.updateBook(id,book))
    }

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: String): ResponseEntity<String> {
        bookService.deleteBook(id)
        val message = "Book with ID $id has been deleted successfully"
        println(message)
        return ResponseEntity.ok(message)
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: String): ResponseEntity<Books> {
        return ResponseEntity.ok(bookService.getBookById(id))
    }

    @GetMapping
    fun listAllBooks(): ResponseEntity<List<Books>> {
        return ResponseEntity.ok(bookService.listAllBooks())
    }

    @GetMapping("/search")
    fun searchBooks(@RequestParam title: String?): ResponseEntity<List<Books>> {
        return ResponseEntity.ok(bookService.searchBooksByTitle(title ?: ""))
    }
}