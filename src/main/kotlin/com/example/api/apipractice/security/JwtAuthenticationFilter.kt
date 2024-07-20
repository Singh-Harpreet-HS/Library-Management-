package com.example.api.apipractice.security

import com.example.api.apipractice.services.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

//@Component
//class JwtAuthenticationFilter(
//    private val jwtService: JwtService,
//    @Lazy private val userService: UserService
//): OncePerRequestFilter() {
//
//    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//        val header = request.getHeader("Authorization")
//        if (header != null && header.startsWith("Bearer ")) {
//            val token = header.substring(7)
//            val roles = jwtService.extractUserRoles(token)
//            val username = jwtService.extractUsername(token)
//            if (username != null && SecurityContextHolder.getContext().authentication == null) {
//                val userDetails = userService.loadUserByUsername(username)
//                if (jwtService.validateToken(token, userDetails)) {
//                    //
//                    val authorities = userDetails.authorities.map { it.authority }
//                    println("User authorities: $authorities") // Add this line for debugging
//                    //
//                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
//                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
//                    SecurityContextHolder.getContext().authentication = authentication
//                }else {
//                    println("Token validation failed")
//                }
//            } else {
//                println("Username is null or already authenticated")
//            }
//        }else {
//            println("No Bearer token found in header")
//        }
//        filterChain.doFilter(request, response)
//    }
//}


//@Component
//class JwtAuthenticationFilter(
//    private val jwtService: JwtService,
//    @Lazy private val userService: UserService
//) : OncePerRequestFilter() {
//
//    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
//        val header = request.getHeader("Authorization")
//        if (header != null && header.startsWith("Bearer ")) {
//            val token = header.substring(7)
//            try {
//                val username = jwtService.extractUsername(token)
//                if (username != null && SecurityContextHolder.getContext().authentication == null) {
//                    val userDetails = userService.loadUserByUsername(username)
//                    if (jwtService.validateToken(token, userDetails)) {
//
//                        val roles = jwtService.extractUserRoles(token)
//                        val authorities = roles.map { role -> role.toUpperCase() } // Ensure roles are uppercase
//                            .map { role -> "ROLE_$role" } // Spring Security expects roles in this format
//                            .map { role -> JwtGrantedAuthority(role) } // Custom JwtGrantedAuthority
//                        println("User authorities: ${userDetails.authorities.map { it.authority }}") // Add this line for debugging
//                        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, authorities)
//                        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
//                        SecurityContextHolder.getContext().authentication = authentication
//                    } else {
//                        println("Token validation failed")
//                    }
//                } else {
//                    println("Username is null or already authenticated")
//                }
//            } catch (e: Exception) {
//                println("Failed to process authentication token: ${e.message}")
//            }
//        } else {
//            println("No Bearer token found in header")
//        }
//        filterChain.doFilter(request, response)
//    }
//}
//
///**
// * Custom implementation of GrantedAuthority to handle roles from JWT token.
// */
//class JwtGrantedAuthority(private val role: String) : GrantedAuthority {
//
//    override fun getAuthority(): String {
//        return role
//    }
//}


@Component
class JwtRequestFilter(
    @Lazy private val userService: UserService,  // Inject UserService here
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwt: String?= null

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            username = jwtUtil.extractUsername(jwt)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = this.userService.loadUserByUsername(username)
            if (jwtUtil.validateToken(jwt!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
