package com.example.api.apipractice.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

//@Document(collection = "users")
//data class Users(
//
//    @Id
//    val id:String?=null,
//    val username:String?="Car",
//    val password:String,
//    val roles:List<String> = emptyList()
//)


@Document(collection = "users")
data class Users(
    @Id val id: String?=null,
    val username: String,
    val password: String,
    val roles: List<Role>
)


