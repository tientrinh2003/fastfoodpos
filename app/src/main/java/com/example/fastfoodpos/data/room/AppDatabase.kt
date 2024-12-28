package com.example.fastfoodpos.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.data.room.Entity.FoodItemEntity
import com.example.fastfoodpos.data.room.Entity.OrderEntity

@Database(
    entities = [FoodItemEntity::class, OrderEntity::class], // Add OrderEntity here
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
    abstract fun orderDao(): OrderDao
}
