package com.example.api.apipractice.controllers

import com.example.api.apipractice.model.Reviews
import com.example.api.apipractice.services.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reviews")
class ReviewController(private val reviewService: ReviewService) {
    @PostMapping
    fun addReview(@RequestBody review: Reviews): ResponseEntity<Reviews> {
        return ResponseEntity.ok(reviewService.addReview(review))
    }

    @GetMapping("/book/{bookId}")
    fun getReviewsByBookId(@PathVariable bookId: String): ResponseEntity<List<Reviews>> {
        return ResponseEntity.ok(reviewService.getReviewsByBookId(bookId))
    }

    @GetMapping("/user/{userId}")
    fun getReviewsByUserId(@PathVariable userId: String): ResponseEntity<List<Reviews>> {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId))
    }
}