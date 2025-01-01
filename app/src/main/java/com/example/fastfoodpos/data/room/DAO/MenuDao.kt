package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.fastfoodpos.data.room.Entity.FoodItemEntity

@Dao
interface MenuDao {
    @Insert
    suspend fun insertFoodItem(foodItem: FoodItemEntity)

    @Insert
    suspend fun insertFoodItems(foodItems: List<FoodItemEntity>)

    @Query("SELECT * FROM food_items")
    suspend fun getAllFoodItems(): List<FoodItemEntity>

    @Update
    suspend fun updateFoodItem(foodItem: FoodItemEntity)

    @Query("SELECT * FROM food_items WHERE id = :itemId")
    fun getFoodItemById(itemId: Int): FoodItemEntity?

    @Query("UPDATE food_items SET quantity = :newQuantity WHERE id = :itemId")
    suspend fun updateFoodItemQuantity(itemId: Int, newQuantity: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT * FROM food_items WHERE name = :name")
    suspend fun getFoodItemByName(name: String): FoodItemEntity

    @Query("DELETE FROM food_items WHERE id = :itemId")
    suspend fun deleteFoodItem(itemId: Int)

}