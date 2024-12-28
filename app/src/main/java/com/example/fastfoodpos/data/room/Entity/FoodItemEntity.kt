package com.example.fastfoodpos.data.room.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table")
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val price: Double,
    val category: String,
    val description: String,
    val imageUrl: String
)
