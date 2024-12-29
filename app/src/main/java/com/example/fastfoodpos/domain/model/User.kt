package com.example.fastfoodpos.domain.model

// A simple Customer model
data class User(
    val id: Int = 0,
    val name: String,
    val account: String,
    val password: String,
    val role: String
)
