package com.example.fastfoodpos.domain.model

// A simple Customer model
data class Customer(
    val id: Int,
    val name: String,
    val email: String,
    val orderHistory: List<String> // List of past food items ordered
)
