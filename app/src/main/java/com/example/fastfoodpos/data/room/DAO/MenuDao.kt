package com.example.fastfoodpos.data.room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fastfoodpos.data.room.Entity.FoodItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodItems(foodItems: List<FoodItemEntity>)

    @Query("SELECT * FROM food_items")
    fun getAllFoodItems(): Flow<List<FoodItemEntity>>

    @Query("SELECT * FROM food_items WHERE id = :id")
    fun getFoodItemById(id: Int): Flow<FoodItemEntity?>
}
