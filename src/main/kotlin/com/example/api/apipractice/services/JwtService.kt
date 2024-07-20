package com.example.api.apipractice.services

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

//@Service
//class JwtService {
//
//    @Value("\${jwt.secret}")
//    private lateinit var secret: String
//
//    @Value("\${jwt.expiration}")
//    private lateinit var expirationTime: String
//
//    fun generateToken(username: String, roles: List<String>): String {
////        //val claims = Jwts.claims().setSubject(username)
////        val claims = Jwts.claims().setSubject(username)
////        claims["roles"] = roles
//        val now = Date()
//        val validity = Date(now.time + expirationTime.toLong() * 1000)
//
//        return Jwts.builder()
//            .setHeaderParam("typ", "JWT") // Setting token type
//            .setSubject(username)
//            .claim("roles", roles.map { it.toUpperCase() })
//            .setIssuedAt(now)
//            .setExpiration(validity)
//            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
//            .compact()
//    }
//
//    fun extractUsername(token: String): String {
//        return getClaims(token).subject
//    }
//
//    fun validateToken(token: String, userDetails: UserDetails): Boolean {
//        val username = extractUsername(token)
//        val claims = getClaims(token)
//
//        return (username == userDetails.username && !claims.expiration.before(Date()))
//
//    }
//
//    fun extractUserRoles(token: String): List<String> {
//        val claims = getClaims(token)
//        val roles = claims["roles"] as List<String>? ?: throw IllegalArgumentException("Roles not found in JWT token")
//        return roles.map { it.toUpperCase() } // Ensure roles are uppercase
//    }
//
//    private fun getClaims(token: String): Claims {
//        return Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(token).body
//    }
//}