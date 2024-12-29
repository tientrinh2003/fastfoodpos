package com.example.fastfoodpos.domain.model

data class CartItem(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageResource: String
)