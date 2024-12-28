package com.example.fastfoodpos.domain.model

// A simple Customer model
data class Customer(
    val id: Int = 0,
    val name: String,
    val email: String,
    val role: String
)
