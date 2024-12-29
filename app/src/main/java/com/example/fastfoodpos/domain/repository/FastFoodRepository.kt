package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface FastFoodRepository {
    fun fetchFoodItems(): Flow<List<FoodItem>>
    suspend fun insertFoodItem(foodItem: FoodItem)
    suspend fun insertFoodItems(foodItems: List<FoodItem>)
    suspend fun saveOrderToLocal(order: Order)
    fun fetchOrdersFromLocal(): Flow<List<Order>>
    suspend fun getFoodItemById(itemId: Int): FoodItem?
    suspend fun updateFoodItem(foodItem: FoodItem)
}