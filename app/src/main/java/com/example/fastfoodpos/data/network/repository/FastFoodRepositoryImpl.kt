package com.example.fastfoodpos.data.repository

import com.example.fastfoodpos.data.network.DTO.FoodItemDTO
import com.example.fastfoodpos.data.network.FastFoodApi
import com.example.fastfoodpos.data.room.DAO.MenuDao
import com.example.fastfoodpos.data.room.DAO.OrderDao
import com.example.fastfoodpos.data.room.Entity.OrderEntity
import com.example.fastfoodpos.domain.model.Customer
import com.example.fastfoodpos.domain.model.Order
import com.example.fastfoodpos.domain.repository.FastFoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FastFoodRepositoryImpl @Inject constructor(
    private val fastFoodApi: FastFoodApi,
    private val menuDao: MenuDao,
    private val orderDao: OrderDao
) : FastFoodRepository {

    override suspend fun fetchFoodItems(): List<FoodItemDTO> = withContext(Dispatchers.IO) {
        try {
            val response = fastFoodApi.getFoodItems()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("API Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            println("Error fetching food items: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getOrdersFromLocal(): List<Order> = withContext(Dispatchers.IO) {
        orderDao.getAllOrders().map { entity ->
            Order(
                id = entity.id,
                orderDate = entity.orderDate,
                totalPrice = entity.totalPrice,
                items = entity.items.split(",")
            )
        }
    }

    override suspend fun getCustomerDetails(): Customer = withContext(Dispatchers.IO) {
        Customer(
            id = 1,
            name = "John Doe",
            email = "johndoe@example.com",
            orderHistory = listOf("Burger", "Fries", "Coke")
        )
    }

    suspend fun saveOrderToLocal(order: Order) = withContext(Dispatchers.IO) {
        orderDao.insertOrder(
            OrderEntity(
                id = order.id,
                orderDate = order.orderDate,
                totalPrice = order.totalPrice,
                items = order.items.joinToString(",")
            )
        )
    }
}
