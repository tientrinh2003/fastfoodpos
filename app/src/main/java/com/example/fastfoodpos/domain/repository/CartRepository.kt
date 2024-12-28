package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(cartItem: CartItemEntity)
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun clearCart()
}
