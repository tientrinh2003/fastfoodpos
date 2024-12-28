package com.example.fastfoodpos.domain.model

data class FoodItem(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val description: String,
    val imageUrl: String
)
