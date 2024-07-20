package com.example.api.apipractice.services

import com.example.api.apipractice.model.Users
import com.example.api.apipractice.repository.UserRepository
import com.mongodb.MongoSocketReadException
import org.springframework.retry.annotation.Retryable
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
    ): UserDetailsService
{
    @Retryable(value = [MongoSocketReadException::class], maxAttempts = 5)
    fun registerUser(user: Users): Users {
        // Encrypt the password before saving
        val encryptedPassword =  passwordEncoder.encode(user.password)
        val newUser = user.copy(password = encryptedPassword)
        return userRepository.save(newUser)
    }

    @Retryable(value = [MongoSocketReadException::class], maxAttempts = 5)
    fun findByUsername(username: String): Users? {
        val optionalUser = userRepository.findByUsername(username)
        return optionalUser.orElse(null)
    }

    @Retryable(value = [MongoSocketReadException::class], maxAttempts = 5)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User not found") }
        val authorities = user.roles.map { SimpleGrantedAuthority(it.role) }
        return User(user.username, user.password, authorities)
    }
}

