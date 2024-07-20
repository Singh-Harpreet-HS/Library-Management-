package com.example.api.apipractice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Role(@Id val id: String, val role: String)