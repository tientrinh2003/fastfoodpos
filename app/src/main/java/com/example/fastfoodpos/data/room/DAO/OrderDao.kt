package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastfoodpos.data.room.Entity.OrderEntity

@Dao
interface OrderDao {

    // Insert a new order into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    // Fetch all orders from the database
    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderEntity> // Fetch all orders
}
