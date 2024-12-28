package com.example.fastfoodpos.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderDate: String, // Store date as a String or a proper Date type
    val totalPrice: Double,
    val items: String // You can store the items as a comma-separated list or JSON string
)
