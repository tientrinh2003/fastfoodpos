package com.example.fastfoodpos.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fastfoodpos.data.room.DAO.CartDAO
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.data.room.DAO.UserDao
import com.example.fastfoodpos.data.room.Entity.CartItemEntity
import com.example.fastfoodpos.data.room.Entity.FoodItemEntity
import com.example.fastfoodpos.data.room.Entity.OrderEntity
import com.example.fastfoodpos.data.room.Entity.UserEntity

@Database(entities = [CartItemEntity::class, FoodItemEntity::class, OrderEntity::class, UserEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class) // Correct usage
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao
    abstract fun orderDao(): OrderDao
    abstract fun cartDao(): CartDAO
    abstract fun userDao(): UserDao
}