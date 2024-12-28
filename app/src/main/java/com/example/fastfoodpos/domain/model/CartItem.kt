package com.example.fastfoodpos.domain.model

data class CartItem(
    val foodId: Int,
    val name: String,
    val price: Double,
    var quantity: Int
)
