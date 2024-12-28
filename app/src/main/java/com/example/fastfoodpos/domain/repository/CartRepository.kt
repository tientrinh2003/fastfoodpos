package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun insertCartItem(cartItem: CartItem)
    suspend fun updateCartItem(cartItem: CartItem)
    suspend fun deleteCartItem(id: Int)
    fun getAllCartItems(): Flow<List<CartItem>>
    suspend fun getCartItemById(id: Int): CartItem?
    suspend fun clearCart()
}