package com.example.api.apipractice.security

import com.example.api.apipractice.model.Users

import com.example.api.apipractice.services.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig(
//    private val jwtAuthenticationFilter: JwtAuthenticationFilter
//){
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http.csrf { it.disable() }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .authorizeHttpRequests { authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/api/users/register", "/api/users/login", "/api/books/**").permitAll()
//                    .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
//                    .requestMatchers("/api/user/**").hasAuthority("USERS") // Allow USER and ADMIN roles
//                    .requestMatchers("/error").permitAll()
//                    .anyRequest().authenticated()
//            }
//            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
//        return http.build()
//    }
//
//    @Bean
//    fun passwordEncoder(): PasswordEncoder {
//        return BCryptPasswordEncoder()
//    }
//
//    @Bean
//    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
//        return authenticationConfiguration.authenticationManager
//    }
//
//    @Bean
//    fun webSecurityCustomizer(): WebSecurityCustomizer {
//        return WebSecurityCustomizer { web -> web.ignoring().requestMatchers("/h2-console/**") }
//    }
//}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
class SecurityConfig(
    private val jwtRequestFilter: JwtRequestFilter,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManagerBean(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }


    @Bean
    fun userDetailsService(passwordEncoder: PasswordEncoder): UserDetailsService {
        val manager = InMemoryUserDetailsManager()
        manager.createUser(
            User.withUsername("user")
                .password(passwordEncoder.encode("userPass"))
                .roles("USER")
                .build()
        )
        manager.createUser(
            User.withUsername("admin")
                .password(passwordEncoder.encode("adminPass"))
                .roles("USER", "ADMIN")
                .build()
        )
        return manager
    }

//    @Bean
//    fun configureAuthenticationManagerBuilder(
//        auth: AuthenticationManagerBuilder,
//        userDetailsService: UserDetailsService,
//        passwordEncoder: PasswordEncoder
//    ): AuthenticationManagerBuilder {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
//        return auth
//    }


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                    .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/login/**").permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic { it }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico")
        }
    }
}
