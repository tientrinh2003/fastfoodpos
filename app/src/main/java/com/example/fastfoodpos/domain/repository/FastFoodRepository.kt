package com.example.fastfoodpos.domain.repository

import com.example.fastfoodpos.data.network.DTO.FoodItemDTO
import com.example.fastfoodpos.domain.model.Customer
import com.example.fastfoodpos.domain.model.Order

interface FastFoodRepository {
    suspend fun fetchFoodItems(): List<FoodItemDTO>
    suspend fun getOrdersFromLocal(): List<Order> // Method to fetch orders
    suspend fun getCustomerDetails(): Customer // Add this method
}
