package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.domain.model.Customer
import com.example.fastfoodpos.domain.model.FoodItem
import com.example.fastfoodpos.domain.model.Order

interface FastFoodRepository {
    suspend fun fetchFoodItems(): List<FoodItem>
    suspend fun getOrdersFromLocal(): List<Order> // Method to fetch orders
    suspend fun getCustomerDetails(): Customer // Add this method
}
