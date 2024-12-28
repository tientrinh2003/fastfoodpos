package com.example.fastfoodpos.data.repository.impl

import com.example.fastfoodpos.data.room.DAO.CartDAO
import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import com.example.fastfoodpos.domain.model.CartItem
import com.example.fastfoodpos.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(private val cartDao: CartDAO) : CartRepository {
    override suspend fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem.toCartItemEntity())
    }

    override suspend fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem.toCartItemEntity())
    }

    override suspend fun deleteCartItem(id: Int) {
        cartDao.deleteCartItem(id)
    }

    override fun getAllCartItems(): Flow<List<CartItem>> {
        return cartDao.getAllCartItems().map { cartItemEntities ->
            cartItemEntities.map { it.toCartItem() }
        }
    }

    override suspend fun getCartItemById(id: Int): CartItem? {
        return cartDao.getCartItemById(id)?.toCartItem()
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}

// Extension functions to convert between CartItem and CartItemEntity
fun CartItem.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        name = name,
        price = price,
        quantity = quantity
    )
}

fun CartItemEntity.toCartItem(): CartItem {
    return CartItem(
        id = id,
        name = name,
        price = price,
        quantity = quantity
    )
}