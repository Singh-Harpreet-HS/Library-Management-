package com.example.api.apipractice.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate

@Configuration
@EnableRetry
class RetryConfig {

    @Bean
    fun retryTemplate(): RetryTemplate {
        val retryTemplate = RetryTemplate()
        val retryPolicy = SimpleRetryPolicy()
        retryPolicy.maxAttempts = 5
        retryTemplate.setRetryPolicy(retryPolicy)
        return retryTemplate
    }
}