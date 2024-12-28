package com.example.fastfoodpos.data.repository

import com.example.fastfoodpos.data.room.DAO.CartDAO
import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import com.example.fastfoodpos.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDAO) : CartRepository {
    override suspend fun addToCart(cartItem: CartItemEntity) = cartDao.insert(cartItem)
    override fun getCartItems(): Flow<List<CartItemEntity>> = cartDao.getAllCartItems()
    override suspend fun clearCart() = cartDao.clearCart()
}
