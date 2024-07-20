package com.example.api.apipractice.services

import com.example.api.apipractice.model.Reviews
import com.example.api.apipractice.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun addReview(review: Reviews): Reviews {
        return reviewRepository.save(review)
    }

    fun getReviewsByBookId(bookId: String): List<Reviews> {
        return reviewRepository.findByBookId(bookId)
    }

    fun getReviewsByUserId(userId: String): List<Reviews> {
        return reviewRepository.findByUserId(userId)
    }
}