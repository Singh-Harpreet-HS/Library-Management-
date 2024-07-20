package com.example.api.apipractice.controllers

import com.example.api.apipractice.model.AuthenticationRequest
import com.example.api.apipractice.model.AuthenticationResponse
import com.example.api.apipractice.model.Users
import com.example.api.apipractice.security.JwtUtil
import com.example.api.apipractice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
) {
    @PostMapping("/register")
    fun register(@RequestBody user: Users): ResponseEntity<Users> {

//        val newUserRoles = user.roles.toMutableList()  // Convert to mutable list to modify
//        //newUserRoles.add("USERS")  // Ensure USERS role is always added
//
//        val newUser = user.copy(password = passwordEncoder.encode(user.password), roles = newUserRoles.toList())

        val registeredUser = userService.registerUser(user)
        return ResponseEntity.ok(registeredUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody authRequest:AuthenticationRequest): ResponseEntity<Any> {
        val user = userService.findByUsername(authRequest.username)
        return if (user != null) {
            try {
//                val authentication = authenticationManager.authenticate(
//                    UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
//                )
                authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
                )
                val userDetails = userService.loadUserByUsername(authRequest.username)
                val token = jwtUtil.generateToken(userDetails)
                ResponseEntity.ok(AuthenticationResponse(token))
            } catch (e: AuthenticationException) {
                ResponseEntity.status(401).body("Invalid credentials")
            }
        } else {
            ResponseEntity.status(401).body("User not found")
        }
    }

    @GetMapping("/protected")
    fun protectedEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("You have accessed a protected endpoint!")
    }

    @GetMapping("/admin/endpoint")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun adminEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("You have accessed an admin endpoint!")
    }

    @GetMapping("/user/endpoint")
    @PreAuthorize("hasAuthority('ROLE_USERS')")
    fun userEndpoint(): ResponseEntity<String> {
        return ResponseEntity.ok("You have accessed a user endpoint!")
    }
}

