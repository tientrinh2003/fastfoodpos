package com.example.fastfoodpos.domain.model

// Define the Order class with necessary fields
data class Order(
    val id: Int = 0,
    val orderDate: String,
    val totalPrice: Double,
    val items: List<CartItem>
)