package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface FastFoodRepository {
    suspend fun fetchFoodItems(): List<FoodItem>
    suspend fun saveOrderToLocal(order: Order)
    fun fetchOrdersFromLocal(): Flow<List<Order>>
}
