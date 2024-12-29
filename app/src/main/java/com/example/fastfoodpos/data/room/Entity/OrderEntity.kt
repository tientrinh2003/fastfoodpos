package com.example.fastfoodpos.data.room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.fastfoodpos.data.room.CartItemTypeConverter
import com.example.fastfoodpos.domain.model.CartItem

@Entity(tableName = "orders")
@TypeConverters(CartItemTypeConverter::class)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderDate: String,
    val totalPrice: Double,
    @ColumnInfo(name = "items")
    val items: List<CartItem>
)