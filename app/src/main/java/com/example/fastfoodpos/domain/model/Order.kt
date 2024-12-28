package com.example.fastfoodpos.domain.model

// Define the Order class with necessary fields
data class Order(
    val id: Int,
    val orderDate: String, // You can use a Date type, but String works for simplicity
    val totalPrice: Double,
    val items: List<String> // List of food items in the order
)
