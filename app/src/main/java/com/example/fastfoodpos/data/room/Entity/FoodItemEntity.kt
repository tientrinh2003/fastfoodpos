package com.example.fastfoodpos.data.room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fastfoodpos.domain.model.FoodItem

@Entity(tableName = "food_items")
data class FoodItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image_resource") val imageResource: String,
    @ColumnInfo(name = "quantity") val quantity: Int
)

fun FoodItemEntity.toFoodItem(): FoodItem {
    return FoodItem(
        id = id,
        name = name,
        price = price,
        imageResource = imageResource,
        quantity = quantity
    )
}

fun FoodItem.toFoodItemEntity(): FoodItemEntity {
    return FoodItemEntity(
        id = id,
        name = name,
        price = price,
        imageResource = imageResource,
        quantity = quantity
    )
}