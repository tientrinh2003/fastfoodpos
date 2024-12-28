package com.example.fastfoodpos.domain.model

data class FoodItem(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val imageResource: Int
)