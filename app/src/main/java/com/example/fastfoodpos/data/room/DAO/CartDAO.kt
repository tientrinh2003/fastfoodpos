package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteCartItem(id: Int)

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun getCartItemById(id: Int): CartItemEntity?

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

}
