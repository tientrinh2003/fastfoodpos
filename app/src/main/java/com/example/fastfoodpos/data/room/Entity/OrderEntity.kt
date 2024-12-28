package com.example.fastfoodpos.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderDate: String,
    val totalPrice: Double,
    val items: List<String>
)
