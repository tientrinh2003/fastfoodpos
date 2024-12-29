package com.example.fastfoodpos.domain.model

data class FoodItem(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val imageResource: Int,
    val quantity: Int = 0
)
