package com.example.fastfoodpos.data.network.DTO

data class FoodItemDTO(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val description: String,
    val imageUrl: String
)
