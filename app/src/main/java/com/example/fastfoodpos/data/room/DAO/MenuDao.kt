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
    suspend fun insert(foodItem: FoodItemEntity)

    @Query("SELECT * FROM menu_table")
    fun getAllMenuItems(): Flow<List<FoodItemEntity>>
}
