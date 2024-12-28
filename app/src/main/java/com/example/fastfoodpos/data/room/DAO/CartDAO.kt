package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart_table")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("DELETE FROM cart_table")
    suspend fun clearCart()
}
